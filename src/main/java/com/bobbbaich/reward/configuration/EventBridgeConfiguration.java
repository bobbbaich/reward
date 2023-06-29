package com.bobbbaich.reward.configuration;

import io.awspring.cloud.autoconfigure.core.RegionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.services.eventbridge.EventBridgeAsyncClient;

@Slf4j
@Configuration
public class EventBridgeConfiguration {

    @Bean
    public EventBridgeAsyncClient eventBridgeClient(@Value("${aws.region}") String reg,
                                                    AwsCredentialsProvider awsCredentialsProvider,
                                                    RegionProperties regionProperties) {
        log.info("reg=" + reg);
        log.info("awsCredentialsProvider=", awsCredentialsProvider);
        log.info("regionProperties=={}", regionProperties);
        return EventBridgeAsyncClient.builder()
                .build();
    }
}
