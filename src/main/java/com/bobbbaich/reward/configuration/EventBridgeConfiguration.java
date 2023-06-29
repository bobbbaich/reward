package com.bobbbaich.reward.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.eventbridge.EventBridgeAsyncClient;

@Slf4j
@Configuration
public class EventBridgeConfiguration {

    @Bean
    public EventBridgeAsyncClient eventBridgeClient() {
        return EventBridgeAsyncClient.builder()
                .build();
    }
}
