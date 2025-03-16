package com.sstefanov.currencies.gateway.service;

import com.sstefanov.currencies.gateway.configs.RabbitProps;
import com.sstefanov.currencies.gateway.entities.RequestInfo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class StatisticsSender {

    private final RabbitTemplate rabbitTemplate;

    public static final String STATS_KEY = "STATS:";
    public static final String SEPARATOR = "::";

    public void send(String requestId, String clientId, String serviceName, Long timestamp) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            runAsync(() -> sendMessage(requestId, clientId, serviceName, timestamp), executor);
        } catch (Exception e) {
            throw new RuntimeException("Exception: ", e);
        }
    }

    private void sendMessage(String requestId, String clientId, String serviceName, Long timestamp) {
        log.info("Sending message to RabbitMQ, RequestId: {}, clientId: {}, serviceName: {}", requestId, clientId, serviceName);

        String message = STATS_KEY + SEPARATOR + requestId + SEPARATOR + clientId + SEPARATOR +
                serviceName + SEPARATOR + timestamp;

        rabbitTemplate.send(new Message(message.getBytes(StandardCharsets.UTF_8)));
    }

    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    private void sendingMessages() {
        send( "requestId",  "clientId",  "serviceName", 2L);
    }


    @RabbitListener(queues = "rates")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }


}