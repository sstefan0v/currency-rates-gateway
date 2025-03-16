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

@RequestMapping(value = "/json_api" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

}