package com.bobbbaich.reward.configuration;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cloud.sleuth.brave.LocalServiceName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Aspect
@Configuration
public class XRayConfiguration extends AbstractXRayInterceptor {

    @LocalServiceName
    private String serviceName;

    @Bean
    public Filter TracingFilter() {
        return new AWSXRayServletFilter(serviceName);
    }

    @Override
    @Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) && within(com.bobbbaich.reward.*))")
    protected void xrayEnabledClasses() {
    }
}
