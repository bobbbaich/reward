package com.bobbbaich.reward.service;

import java.util.concurrent.CompletableFuture;

public interface SqsProducer {
    <T> CompletableFuture<Void> send(String queueName, T payload);
}
