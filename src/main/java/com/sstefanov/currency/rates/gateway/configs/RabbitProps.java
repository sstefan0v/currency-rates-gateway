package com.sstefanov.currency.rates.gateway.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitProps {
    private String exchangeName;
    private String queueName;
    private String routingKey;
}
