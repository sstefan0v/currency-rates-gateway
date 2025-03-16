package com.sstefanov.currency.rates.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sstefanov.currency.rates.gateway.controller.model.xml.request.current.CommandCurrent;
import com.sstefanov.currency.rates.gateway.controller.model.xml.request.history.CommandHistory;
import com.sstefanov.currency.rates.gateway.service.GatewayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/xml_api", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
public class XmlApiController {

    private final GatewayService gatewayService;
    private final XmlMapper xmlMapper = new XmlMapper();

    @PostMapping(value = "/command")
    public Mono<String> processXml(@RequestBody String xmlPayload) throws JsonProcessingException {
        if (xmlPayload.contains("<get consumer")) {
            CommandCurrent request = parseXml(xmlPayload, CommandCurrent.class);
            return Mono.just(xmlMapper.writeValueAsString(gatewayService.process(request)));
        } else {
            CommandHistory request = parseXml(xmlPayload, CommandHistory.class);
            return Mono.just(xmlMapper.writeValueAsString(gatewayService.process(request)));
        }
    }

    private <T> T parseXml(String xml, Class<T> clazz) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(xml, clazz);
        } catch (Exception e) {
            throw new RuntimeException("XML parsing error", e);
        }
    }
}
