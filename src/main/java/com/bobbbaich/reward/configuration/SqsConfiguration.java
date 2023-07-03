package com.bobbbaich.reward.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import io.awspring.cloud.sqs.operations.SqsTemplateBuilder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SqsConfiguration {

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient,
                                   ObjectProvider<ObjectMapper> objectMapperProvider) {
        SqsTemplateBuilder builder = SqsTemplate.builder();
        objectMapperProvider
                .ifAvailable(om -> builder.configureDefaultConverter(converter -> converter.setObjectMapper(om)));
        return builder.sqsAsyncClient(sqsAsyncClient)
                .configure(options -> options
                        .queueNotFoundStrategy(QueueNotFoundStrategy.FAIL))
                .build();
    }
}
