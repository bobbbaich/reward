package com.bobbbaich.reward.configuration;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import org.springframework.cloud.sleuth.brave.LocalServiceName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class XRayConfiguration {

    @LocalServiceName
    private String serviceName;

    @Bean
    public Filter TracingFilter() {
        return new AWSXRayServletFilter(serviceName);
    }
}
