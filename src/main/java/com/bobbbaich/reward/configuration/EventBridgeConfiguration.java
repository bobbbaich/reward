package com.bobbbaich.reward.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.eventbridge.EventBridgeAsyncClient;

@Configuration
public class EventBridgeConfiguration {

    @Bean
    public EventBridgeAsyncClient eventBridgeClient() {
        return EventBridgeAsyncClient.builder()
                .build();
    }
}
