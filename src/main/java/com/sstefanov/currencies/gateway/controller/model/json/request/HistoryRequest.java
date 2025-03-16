package com.sstefanov.currencies.gateway.controller.model.json.request;

import lombok.Data;

@Data
public class HistoryRequest {
    private String requestId;
    private Long timestamp;
    private String client;
    private String currency;
    private Integer period;
}
