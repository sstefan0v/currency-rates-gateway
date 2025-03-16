package com.sstefanov.currency.rates.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@EnableRabbit
@Configuration
public class RabbitMqConf {

    private final RabbitProps rabbitProps;

    @Bean
    public Queue queue() {
        return new Queue(rabbitProps.getQueueName(), true);
    }

    @Bean
    public DirectExchange setupExchange() {
        return new DirectExchange(rabbitProps.getExchangeName(), true, false);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(rabbitProps.getRoutingKey());
    }
}
