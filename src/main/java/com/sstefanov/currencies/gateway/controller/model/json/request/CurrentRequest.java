package com.sstefanov.currencies.gateway.controller.model.json.request;

import lombok.Data;

@Data
public class CurrentRequest  {
    private String requestId;
    private Long timestamp;
    private String client;
    private String currency;
}
