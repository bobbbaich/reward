package com.bobbbaich.reward.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@RequiredArgsConstructor
@Configuration
@Profile("test")
public class MockMvcConfig {

    @Bean
    public TokenStore jwkTokenStore() {
        return new InMemoryTokenStore();
    }

}
