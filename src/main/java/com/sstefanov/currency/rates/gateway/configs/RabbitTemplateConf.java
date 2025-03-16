package com.sstefanov.currency.rates.gateway.configs;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitTemplateConf {

    private final RabbitProps rabbitProps;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(rabbitProps.getExchangeName());
        rabbitTemplate.setRoutingKey(rabbitProps.getRoutingKey());
        return rabbitTemplate;
    }
}



