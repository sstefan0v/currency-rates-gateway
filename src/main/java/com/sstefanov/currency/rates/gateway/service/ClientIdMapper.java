package com.sstefanov.currency.rates.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ClientIdMapper {
    private static final List<String> serviceIdList = new ArrayList<>() {{
        add("ext_service_1");
        add("ext_service_2");
        add("ext_service_3");
        add("ext_service_4");
        add("ext_service_5");
    }};

    public String getServiceIdForClientId(String clientId) {
        log.debug("Getting serviceId, for Client Id: {}", clientId);
        return serviceIdList.get(new Random().nextInt(serviceIdList.size()));
    }
}
