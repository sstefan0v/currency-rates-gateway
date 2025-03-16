package com.sstefanov.currency.rates.gateway.service;

import com.sstefanov.currency.rates.gateway.entities.RequestInfo;
import com.sstefanov.currency.rates.gateway.repositories.RequestInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestStatisticsSaver {

    private final RequestInfoRepository requestInfoRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public static final String REQ_CACHE_KEY = "REQUEST_";

    public void save(String requestId, String clientId, String serviceName, Long timestamp) {
            saveRequestInfoToDB(requestId, clientId, serviceName, timestamp);
            redisTemplate.opsForValue().set(REQ_CACHE_KEY + requestId, requestId, 1, TimeUnit.HOURS);
    }

    private void saveRequestInfoToDB(String requestId, String clientId, String serviceName, Long timestamp) {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setRequestId(requestId);
        requestInfo.setClientId(clientId);
        requestInfo.setServiceName(serviceName);
        requestInfo.setTimestamp(timestamp);
        requestInfoRepository.save(requestInfo);
    }
}