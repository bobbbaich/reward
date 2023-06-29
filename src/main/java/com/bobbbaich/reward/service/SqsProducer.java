package com.bobbbaich.reward.service;

public interface SqsProducer {
    <T> void send(String queueName, T payload);
}
