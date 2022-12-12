package com.bobbbaich.reward.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableResourceServer
public class OAuth2ResourceServerSecurityConfiguration extends ResourceServerConfigurerAdapter {

    private final OAuth2ResourceServerProperties resource;
    private final CognitoAccessTokenConverter cognitoAccessTokenConverter;

    @Value("${spring.security.oauth2.resourceserver.stateless:true}")
    private boolean stateless;

    @Override
    public void configure(ResourceServerSecurityConfigurer configurer) {
        configurer.stateless(stateless);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/health")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    @Profile("!test")
    public TokenStore jwkTokenStore() {
        return new JwkTokenStore(
                List.of(resource.getJwt().getJwkSetUri()),
                cognitoAccessTokenConverter,
                null
        );
    }
}
