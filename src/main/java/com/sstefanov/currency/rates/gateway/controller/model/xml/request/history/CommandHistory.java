package com.sstefanov.currency.rates.gateway.controller.model.xml.request.history;

import com.sstefanov.currency.rates.gateway.controller.model.RequestMessage;
import lombok.Data;

@Data
public class CommandHistory implements RequestMessage {
    private String id;
    private History history;
    private Long timestamp;

    @Override
    public String getRequestId() {
        return id;
    }

    @Override
    public String getCurrency() {
        return history.getCurrency();
    }

    @Override
    public String getClientId() {
        return history.getConsumer();
    }

    @Override
    public Long getTimestamp() {
        if (timestamp == null) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    @Override
    public Integer getPeriod() {
        return history.getPeriod();
    }
}



