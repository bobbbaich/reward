package com.bobbbaich.reward.controller;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
class MvcIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    private JwtDecoder jwtDecoder;

    protected void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    protected Collection<GrantedAuthority> getAuthorities(String... scopes) {
        return Arrays.stream(scopes)
                .map(scope -> (GrantedAuthority) new SimpleGrantedAuthority(scope))
                .toList();
    }
}
