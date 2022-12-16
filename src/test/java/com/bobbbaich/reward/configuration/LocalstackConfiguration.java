package com.bobbbaich.reward.configuration;

import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;

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
}
