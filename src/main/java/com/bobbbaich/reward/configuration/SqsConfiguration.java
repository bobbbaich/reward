package com.bobbbaich.reward.configuration;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import static io.awspring.cloud.sqs.listener.QueueNotFoundStrategy.FAIL;

@Configuration
public class SqsConfiguration {

    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory
                .builder()
                .configure(options -> options
                        .queueNotFoundStrategy(FAIL)
                )
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }
}
