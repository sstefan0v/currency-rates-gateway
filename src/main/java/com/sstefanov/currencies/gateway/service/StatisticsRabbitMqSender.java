package com.sstefanov.currencies.gateway.service;

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

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class StatisticsRabbitMqSender {

    private final RabbitTemplate rabbitTemplate;

    public static final String STATS_KEY = "STATS:";
    public static final String SEPARATOR = "::";

    public void send(String... statsString) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            runAsync(() -> sendMessage(statsString), executor);
        } catch (Exception e) {
            throw new RuntimeException("Exception: ", e);
        }
    }

    private void sendMessage(String... statsString) {
        String message = STATS_KEY + String.join(SEPARATOR, statsString);

        log.info("Sending message to RabbitMQ, Stats: {}", message);
        rabbitTemplate.send(new Message(message.getBytes(StandardCharsets.UTF_8)));
    }

    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    private void sendingMessages() {
        send("requestId", "clientId", "serviceName", Long.toString(2));
    }

    @RabbitListener(queues = "rates")
    private void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }
}