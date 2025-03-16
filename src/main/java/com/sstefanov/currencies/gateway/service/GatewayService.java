package com.sstefanov.currencies.gateway.service;

import com.sstefanov.currencies.gateway.controller.model.RequestMessage;
import com.sstefanov.currencies.gateway.dto.CurrencyRatesRootDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatewayService {

    private final RequestStatisticsSaver requestStatisticsSaver;
    private final RequestValidator requestValidator;
    private final CurrencyRatesService currencyRatesService;
    private final StatisticsRabbitMqSender statisticsRabbitMqSender;
    private final ClientIdMapper clientIdMapper;


    public CurrencyRatesRootDTO process(RequestMessage request) {
        return process(request.getRequestId(),
                request.getCurrency(),
                request.getClientId(),
                clientIdMapper.getServiceIdForClientId(request.getClientId()),
                request.getTimestamp(),
                request.getPeriod());
    }

    private CurrencyRatesRootDTO process(String requestId, String currency, String clientId, String serviceName,
                                         Long timestamp, Integer period) {
        statisticsRabbitMqSender.send(requestId, currency, clientId, serviceName, timestamp.toString(), period.toString());
        requestValidator.validateRequest(requestId);
        CurrencyRatesRootDTO dto = currencyRatesService.getRates(currency, period);
        requestStatisticsSaver.save(requestId, clientId, serviceName, timestamp);
        return dto;
    }
}
