package com.bobbbaich.reward.controller;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
class MvcIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @Autowired
    public MockMvc mockMvc;

    protected void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    protected OAuth2Authentication oauth2(String... scopes) {
        OAuth2Request storedRequest = new OAuth2Request(Map.of(), UUID.randomUUID().toString(), getAuthorities(scopes), true, Set.of(scopes), Set.of(), BASE_URI + port, Set.of(), Map.of());
        return new OAuth2Authentication(storedRequest, null);
    }

    private List<SimpleGrantedAuthority> getAuthorities(String... scopes) {
        return Arrays.stream(scopes)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
