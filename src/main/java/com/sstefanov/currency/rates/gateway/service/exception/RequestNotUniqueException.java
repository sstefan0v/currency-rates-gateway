package com.sstefanov.currency.rates.gateway.service.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class RequestNotUniqueException extends ResponseStatusException {
    public RequestNotUniqueException(HttpStatusCode statusCode, String message) {
        super(statusCode, message);
    }
}
