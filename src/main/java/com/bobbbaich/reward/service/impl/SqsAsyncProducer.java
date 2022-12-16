package com.bobbbaich.reward.service.impl;

import com.bobbbaich.reward.service.SqsProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;

import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Component
public class SqsAsyncProducer implements SqsProducer {

    private final SqsAsyncClient sqsAsyncClient;
    private final ObjectMapper objectMapper;

    public <T> CompletableFuture<Void> send(String queueName, T payload) {
        return sqsAsyncClient.getQueueUrl(request -> request.queueName(queueName))
                .thenApply(GetQueueUrlResponse::queueUrl)
                .thenCompose(queueUrl -> sendToUrl(queueUrl, payload));
    }

    private <T> CompletableFuture<Void> sendToUrl(String queueUrl, T payload) {
        return sqsAsyncClient
                .sendMessage(request -> request
                        .messageBody(getMessageBodyAsJson(payload))
                        .queueUrl(queueUrl))
                .thenRun(() -> log.debug("sent message queueUrl={} payload={}", queueUrl, payload));
    }

    private <T> String getMessageBodyAsJson(T payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(format("Error converting payload=%s", payload), e);
        }
    }
}
