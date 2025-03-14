package com.sstefanov.currencies.gateway.controller.model.xml.request.current;

import lombok.Data;

@Data
public class Get {
    private String currency;
    private String consumer;
}
