package com.sstefanov.currencies.gateway.controller.model.xml.request.current;

import com.sstefanov.currencies.gateway.controller.model.RequestMessage;
import lombok.Data;

@Data
public class CommandCurrent implements RequestMessage {
    private Get get;
    private String id;
    private Long timestamp;

    @Override
    public String getRequestId() {
        return id;
    }

    @Override
    public String getCurrency() {
        return get.getCurrency();
    }

    @Override
    public String getClientId() {
        return get.getConsumer();
    }

    @Override
    public Long getTimestamp() {
        if (timestamp == null) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}



