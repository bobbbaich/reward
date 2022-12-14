package com.bobbbaich.reward.configuration;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * <a href="https://spring.io/blog/2022/10/12/observability-with-spring-boot-3">Observability with Spring Boot 3</a>
 * <p>
 * <a href="https://github.com/marcingrzejszczak/observability-boot-blog-post">Code Sample</a>
 */
@Configuration(proxyBeanMethods = false)
class MicrometerConfiguration {

    // To have the @Observed support we need to register this aspect
    @Bean
    public ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
        return new ObservedAspect(observationRegistry);
    }

    // IMPORTANT! To instrument RestTemplate you must inject the RestTemplateBuilder
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
