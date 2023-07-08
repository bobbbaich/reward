package com.bobbbaich.reward.configuration;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import static io.awspring.cloud.sqs.listener.QueueNotFoundStrategy.FAIL;

@Configuration
public class SqsConfiguration {

    private static final QueueNotFoundStrategy QUEUE_NOT_FOUND_STRATEGY = FAIL;

//    @Bean
//    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient,
//                                   ObjectProvider<ObjectMapper> objectMapperProvider) {
//        SqsTemplateBuilder builder = SqsTemplate.builder();
//        objectMapperProvider
//                .ifAvailable(om -> builder.configureDefaultConverter(converter -> converter.setObjectMapper(om)));
//        return builder.sqsAsyncClient(sqsAsyncClient)
//                .configure(options -> options
//                        .queueNotFoundStrategy(QUEUE_NOT_FOUND_STRATEGY))
//                .build();
//    }

    @Bean
    public SqsMessageListenerContainerFactory<Object> sqsMessageListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory
                .builder()
                .configure(options -> options
                        .queueNotFoundStrategy(QUEUE_NOT_FOUND_STRATEGY)
                )
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }
}
