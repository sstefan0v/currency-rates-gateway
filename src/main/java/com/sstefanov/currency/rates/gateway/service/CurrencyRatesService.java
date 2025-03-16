package com.sstefanov.currency.rates.gateway.service;

import com.sstefanov.currency.rates.gateway.dto.CurrencyRatesRootDTO;
import com.sstefanov.currency.rates.gateway.dto.RateDTO;
import com.sstefanov.currency.rates.gateway.entity.CurrencyRate;
import com.sstefanov.currency.rates.gateway.repository.CurrencyRatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyRatesService {
    private final CurrencyRatesRepository currencyRatesRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String RATES_BASE_KEY = "rates_";

    public CurrencyRatesRootDTO getRates(String baseCurrency, Integer period) {
        final String redisKey = RATES_BASE_KEY + baseCurrency + "_" + period;
        CurrencyRatesRootDTO ratesRootDTO = (CurrencyRatesRootDTO) redisTemplate.opsForValue().get(redisKey);
        log.debug("Get currency rates from Cache for key: {}, value: {}", redisKey, ratesRootDTO);
        if (ratesRootDTO == null) {
            List<CurrencyRate> currencyRates = getRatesFromDB(baseCurrency, period);
            ratesRootDTO = toDTOs(currencyRates, baseCurrency);
            redisTemplate.opsForValue().set(redisKey, ratesRootDTO, 1, TimeUnit.HOURS);
            log.debug("New value persisted into cache for key: {}", redisKey);
        }
        return ratesRootDTO;
    }

    private List<CurrencyRate> getRatesFromDB(String baseCurrency, Integer minusHours) {
        if (minusHours > 0) {
            log.debug("Getting historic rates from DB for period of: {}", minusHours);
            return currencyRatesRepository.findHistoricRatesForBaseCurrency(baseCurrency, calculatePeriodBack(minusHours));
        }
        log.debug("Getting newest rates from DB.");
        return currencyRatesRepository.findNewestRatesForBaseCurrency(baseCurrency);
    }

    private Long calculatePeriodBack(Integer minusHours) {
        return LocalDateTime.now(ZoneOffset.UTC)
                .withMinute(0)
                .withSecond(0)
                .withNano(0)
                .minusHours(minusHours)
                .toInstant(ZoneOffset.UTC)
                .toEpochMilli();
    }

    private CurrencyRatesRootDTO toDTOs(List<CurrencyRate> currencyRatesEnt, String baseCurrency) {
        Map<Long, List<CurrencyRate>> currencyRateMap = currencyRatesEnt.stream()
                .collect(Collectors.groupingBy(rate -> rate.getId().getTimestamp()));
        List<RateDTO> rateDtoList = new ArrayList<>();
        currencyRateMap.keySet()
                .forEach(dateTime -> {
                    Map<String, BigDecimal> rates = new HashMap<>();
                    rateDtoList.add(RateDTO.builder().rates(rates).timestamp(dateTime).build());
                    currencyRateMap.get(dateTime)
                            .forEach(rate -> rates.put(rate.getId().getCurrency(), rate.getRate()));
                });
        return CurrencyRatesRootDTO.builder().baseCurrency(baseCurrency).rates(rateDtoList).build();
    }
}
