package com.sstefanov.currency.rates.gateway.service.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@Service
@Order(-2)
@RequiredArgsConstructor
public class RequestNotUniqueExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper jsonMapper;
    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();

        HttpStatus status = determineHttpStatus(ex);
        response.setStatusCode(status);

        Map<String, Object> errorAttributes = createErrorAttributes(ex, status);
        MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
        if (mediaType == null ) {
            mediaType = MediaType.APPLICATION_JSON;
        }
        response.getHeaders().setContentType(mediaType);

        if (mediaType.equals(MediaType.APPLICATION_XML)) {
            return response.writeWith(
                    Mono.just(response.bufferFactory().wrap(convertToXml(errorAttributes).getBytes())));
        } else {
            return response.writeWith(
                    Mono.just(response.bufferFactory().wrap(convertToJson(errorAttributes).getBytes())));
        }
    }

    private HttpStatus determineHttpStatus(Throwable ex) {
        if (ex instanceof ResponseStatusException) {
            return HttpStatus.valueOf(((ResponseStatusException) ex).getStatusCode().value());
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private Map<String, Object> createErrorAttributes(Throwable ex, HttpStatus status) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("message", ex.getMessage() != null ? ex.getMessage() : "No message available");
        errorAttributes.put("exception", ex.getClass().getName());
        return errorAttributes;
    }

    private String convertToJson(Map<String, Object> errorAttributes) {
        try {
            return jsonMapper.writeValueAsString(errorAttributes);
        } catch (JsonProcessingException e) {
            return "{\"error\":500}";
        }
    }

    private String convertToXml(Map<String, Object> errorAttributes) {
        try {
            return xmlMapper.writeValueAsString(errorAttributes);
        } catch (JsonProcessingException e) {
            return "<error></error>";
        }
    }
}