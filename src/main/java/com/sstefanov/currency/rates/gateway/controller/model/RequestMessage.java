package com.sstefanov.currency.rates.gateway.controller.model;

public interface RequestMessage {
    String getRequestId();
    String getCurrency();
    String getClientId();
    Long getTimestamp();

    default Integer getPeriod() {
        return 0;
    }
}

