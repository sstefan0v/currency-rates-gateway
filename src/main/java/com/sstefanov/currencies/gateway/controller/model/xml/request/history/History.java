package com.sstefanov.currencies.gateway.controller.model.xml.request.history;

import lombok.Data;

@Data
public class History {
    private String consumer;
    private String currency;
    private Integer period;
}
