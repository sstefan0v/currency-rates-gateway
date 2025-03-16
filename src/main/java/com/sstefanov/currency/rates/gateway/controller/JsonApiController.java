package com.sstefanov.currency.rates.gateway.controller;

import com.sstefanov.currency.rates.gateway.controller.model.json.request.CurrentRequest;
import com.sstefanov.currency.rates.gateway.controller.model.json.request.HistoryRequest;
import com.sstefanov.currency.rates.gateway.service.GatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor

//@RequestMapping(value = "/json_api" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value = "/json_api" , produces = MediaType.APPLICATION_JSON_VALUE)
public class JsonApiController {

    private final GatewayService gatewayService;

    @PostMapping("/current")
    public Object getCurrent(@RequestBody CurrentRequest request) {
        return gatewayService.process(request);
    }

    @PostMapping("/history")
    public Object getHistory(@RequestBody HistoryRequest request) {
        return gatewayService.process(request);
    }

    @GetMapping("/test/{requestId}/{period}")
    public Object test(@PathVariable String requestId, @PathVariable Integer period) {
        CurrentRequest newRequest = new CurrentRequest();
        newRequest.setRequestId(requestId);
        newRequest.setTimestamp(System.currentTimeMillis());
        newRequest.setClient("stefan-client");
        newRequest.setCurrency("EUR");

        if (period == 69) {
            return getCurrent(newRequest);
        }
        HistoryRequest historyRequest = new HistoryRequest();
        historyRequest.setRequestId(requestId);
        historyRequest.setTimestamp(System.currentTimeMillis());
        historyRequest.setClient("stefan-client");
        historyRequest.setPeriod(period);
        historyRequest.setCurrency("EUR");
        return getHistory(historyRequest);

    }
}
