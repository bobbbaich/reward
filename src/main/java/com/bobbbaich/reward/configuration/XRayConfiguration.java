//package com.bobbbaich.reward.configuration;
//
//import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;
//import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;
//import jakarta.servlet.Filter;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//@Aspect
//@Configuration
//@Profile({"!local && !test"})
//public class XRayConfiguration extends AbstractXRayInterceptor {
//
//    @Value(value = "${spring.application.name}")
//    private String serviceName;
//
//    @Bean
//    public Filter TracingFilter() {
//        return new AWSXRayServletFilter(serviceName);
//    }
//
//    @Override
//    @Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) && within(com.bobbbaich..*)")
//    protected void xrayEnabledClasses() {
//    }
//}
