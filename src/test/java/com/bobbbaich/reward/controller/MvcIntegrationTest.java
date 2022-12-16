package com.bobbbaich.reward.controller;

import com.bobbbaich.reward.configuration.LocalstackEnvironmentInitializer;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureMockMvc
@ContextConfiguration(initializers = LocalstackEnvironmentInitializer.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class MvcIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    public MockMvc mockMvc;

    protected void configureRestAssured() {
        RestAssured.port = port;
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    protected Collection<GrantedAuthority> getAuthorities(String... scopes) {
        return Arrays.stream(scopes)
                .map(scope -> (GrantedAuthority) new SimpleGrantedAuthority(scope))
                .toList();
    }
}
