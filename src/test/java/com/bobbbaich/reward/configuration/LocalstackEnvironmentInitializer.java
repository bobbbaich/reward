package com.bobbbaich.reward.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.UUID;

import static com.bobbbaich.reward.configuration.LocalstackConfiguration.LOCALSTACK;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

public class LocalstackEnvironmentInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(Map.of(
                "spring.cloud.aws.sqs.endpoint", LOCALSTACK.getEndpointOverride(SQS).toString(),
                "spring.cloud.aws.region.static", LOCALSTACK.getRegion(),
                "spring.cloud.aws.credentials.access-key", UUID.randomUUID().toString(),
                "spring.cloud.aws.credentials.secret-key", UUID.randomUUID().toString()
        )).applyTo(applicationContext);
    }
}
