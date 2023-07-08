package com.bobbbaich.reward.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

@Slf4j
@Configuration
public class LocalstackConfiguration {

    private static final DockerImageName LOCALSTACK_IMAGE = DockerImageName
            .parse("localstack/localstack:latest");

    @Container
    public static LocalStackContainer LOCALSTACK;

    static {
        LOCALSTACK = new LocalStackContainer(LOCALSTACK_IMAGE)
                .withServices(SQS)
                .withReuse(true);
        LOCALSTACK.start();
    }

    @Value("${service.sqs.gift-lifecycle}")
    private String giftLifecycleQueue;

    @Autowired
    private SqsAsyncClient sqsAsyncClient;

    @PostConstruct
    private void setupAws() {
        createQueue(giftLifecycleQueue);
    }

    private void createQueue(String queueName) {
        sqsAsyncClient
                .createQueue(builder -> builder.queueName(queueName))
                .thenRun(() -> log.info("queue created queueName={}", queueName));
    }
}
