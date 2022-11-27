package com.bobbbaich.reward.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

import java.util.List;

@RequiredArgsConstructor
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServerSecurityConfiguration extends ResourceServerConfigurerAdapter {

    private final ResourceServerProperties resource;
    private final CognitoAccessTokenConverter cognitoAccessTokenConverter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/health")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    public TokenStore jwkTokenStore() {
        return new JwkTokenStore(
                List.of(resource.getJwk().getKeySetUri()),
                cognitoAccessTokenConverter,
                null
        );
    }
}
