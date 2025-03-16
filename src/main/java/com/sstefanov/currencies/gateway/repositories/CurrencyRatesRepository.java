package com.sstefanov.currencies.gateway.repositories;

import com.sstefanov.currencies.gateway.entities.CurrencyRate;
import com.sstefanov.currencies.gateway.entities.CurrencyRateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrencyRatesRepository extends JpaRepository<CurrencyRate, CurrencyRateId> {

    @Query(value = "SELECT * FROM currency_rates c WHERE c.base_currency = :baseCurrency " +
            "AND c.timestamp >= (SELECT MAX(timestamp) FROM currency_rates)", nativeQuery = true)
    List<CurrencyRate> findNewestRatesForBaseCurrency(String baseCurrency);

    @Query(value = "SELECT * FROM currency_rates c WHERE c.base_currency = :baseCurrency " +
            "AND c.timestamp >= :period", nativeQuery = true)
    List<CurrencyRate> findHistoricRatesForBaseCurrency(@Param("baseCurrency") String baseCurrency, @Param("period") Long period);
}