package com.sstefanov.currency.rates.gateway.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "currency_rates")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRate {

    @EmbeddedId
    private CurrencyRateId id;

    @Column(nullable = false, precision = 20, scale = 10)
    private BigDecimal rate;
}
