package com.sstefanov.currencies.gateway.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateDTO {
    @JacksonXmlProperty
    private Long timestamp;
    @JacksonXmlProperty
    private Map<String, BigDecimal> rates;
}
