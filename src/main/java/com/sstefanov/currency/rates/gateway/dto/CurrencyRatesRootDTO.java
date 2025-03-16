package com.sstefanov.currency.rates.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyRatesRootDTO {
    @JsonProperty("base")
    @JacksonXmlProperty(localName = "base")
    private String baseCurrency;
    @JacksonXmlProperty(localName = "rates")
    private List<RateDTO> rates;
}