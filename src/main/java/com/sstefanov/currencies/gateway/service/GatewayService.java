package com.sstefanov.currencies.gateway.service;

import com.sstefanov.currencies.gateway.controller.model.json.request.CurrentRequest;
import com.sstefanov.currencies.gateway.controller.model.json.request.HistoryRequest;
import com.sstefanov.currencies.gateway.controller.model.xml.request.current.CommandCurrent;
import com.sstefanov.currencies.gateway.controller.model.xml.request.history.CommandHistory;
import com.sstefanov.currencies.gateway.dto.CurrencyRatesRootDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class GatewayService {

    private final RequestStatisticsSaver requestStatisticsSaver;
    private final RequestValidator requestValidator;
    private final CurrencyRatesService currencyRatesService;

    public CurrencyRatesRootDTO process(CurrentRequest request) {
        return process(request.getRequestId(), request.getCurrency(), request.getClient(),
                "ext_service_2", request.getTimestamp(), 0);
    }

    public CurrencyRatesRootDTO process(HistoryRequest request) {
        return process(request.getRequestId(), request.getCurrency(), request.getClient(),
                "ext_service_2", request.getTimestamp(), request.getPeriod());
    }

    public CurrencyRatesRootDTO process(CommandCurrent request) {
        return process(request.getId(), request.getGet().getCurrency(), request.getGet().getConsumer(),
                "ext_service_1", System.currentTimeMillis(), 0);
    }

    public CurrencyRatesRootDTO process(CommandHistory request) {
        return process(request.getId(), request.getHistory().getCurrency(), request.getHistory().getConsumer(),
                "ext_service_1", System.currentTimeMillis(), request.getHistory().getPeriod());
    }

    private CurrencyRatesRootDTO process(String requestId, String currency, String clientId, String serviceName,
                                         Long timestamp, Integer period) {
        requestValidator.validateRequest(requestId);
        CurrencyRatesRootDTO dto = currencyRatesService.getRates(currency, period);
        requestStatisticsSaver.save(requestId, clientId, serviceName, timestamp);
        return dto;
    }
}
