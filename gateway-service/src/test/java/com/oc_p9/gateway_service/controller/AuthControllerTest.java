package com.oc_p9.gateway_service.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.oc_p9.gateway_service.util.JwtUtil;

public class AuthControllerTest {

    private WebTestClient webClient;
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        AuthController controller = new AuthController(jwtUtil);

        webClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void loginWithValidCredentialsShouldReturnTokenAndSetCookie() {
        when(jwtUtil.generateToken("admin")).thenReturn("mocked-token");

        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "admin";
        request.password = "password";

        webClient.post()
                .uri("/auth/login")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().exists(HttpHeaders.SET_COOKIE)
                .expectBody()
                .jsonPath("$.token").isEqualTo("mocked-token");

        verify(jwtUtil, times(1)).generateToken("admin");
    }

    @Test
    void loginWithInvalidCredentialsShouldReturnUnauthorized() {
        AuthController.AuthRequest request = new AuthController.AuthRequest();
        request.username = "user";
        request.password = "wrong";

        webClient.post()
                .uri("/auth/login")
                .bodyValue(request)
                .exchange()
                .expectStatus().isUnauthorized()
                .expectHeader().doesNotExist(HttpHeaders.SET_COOKIE);

        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void logoutShouldReturnOkAndClearCookie() {
        webClient.post()
                .uri("/auth/logout")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().value(HttpHeaders.SET_COOKIE, cookie -> {
                    assert cookie.contains("jwt=");
                    assert cookie.contains("Max-Age=0");
                });
    }
}
