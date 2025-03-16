package com.sstefanov.currency.rates.gateway.service;
import com.sstefanov.currency.rates.gateway.service.exception.RequestNotUniqueException;
import com.sstefanov.currency.rates.gateway.repository.RequestInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import static com.sstefanov.currency.rates.gateway.service.RequestStatisticsSaver.REQ_CACHE_KEY;

@Service
@RequiredArgsConstructor
public class RequestValidator {

    private final RequestInfoRepository requestInfoRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public void validateRequest(String requestId) {
        if (isRequestRepeated(requestId)) {
            throw new RequestNotUniqueException(HttpStatusCode.valueOf(400), "RequestId WAS repeated: " + requestId);
        }
    }

    private boolean isRequestRepeated(final String requestId) {
        if (redisTemplate.opsForValue().get(REQ_CACHE_KEY + requestId) != null) {
            return true;
        }
        return requestInfoRepository.findRequestId(requestId).orElse(null) != null;
    }
}