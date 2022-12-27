package com.bobbbaich.reward.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimAccessor;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

import java.time.ZonedDateTime;
import java.util.Optional;

@Slf4j
@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider", auditorAwareRef = "auditorProvider")
public class DatabaseConfiguration {

    //remove me
    @Autowired
    private AwsCredentialsProvider containerCredentialsProvider;

    @Bean // Makes ZonedDateTime compatible with auditing fields
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(ZonedDateTime.now());
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        AwsCredentials awsCredentials = containerCredentialsProvider.resolveCredentials();
        log.info("awsCredentials={}", awsCredentials);
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(Jwt.class::cast)
                .map(JwtClaimAccessor::getSubject);
    }
}
