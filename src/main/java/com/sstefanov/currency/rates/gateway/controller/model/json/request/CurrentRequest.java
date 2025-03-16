package com.sstefanov.currency.rates.gateway.controller.model.json.request;

import com.sstefanov.currency.rates.gateway.controller.model.RequestMessage;
import lombok.Data;

@Data
public class CurrentRequest  implements RequestMessage {
    private String requestId;
    private Long timestamp;
    private String client;
    private String currency;

    @Override
    public String getClientId() {
        return client;
    }
}
