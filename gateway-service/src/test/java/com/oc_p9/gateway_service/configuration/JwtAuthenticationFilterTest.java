package com.oc_p9.gateway_service.configuration;

import com.oc_p9.gateway_service.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtUtil jwtUtil;
    private JwtAuthenticationFilter filter;
    private WebFilterChain chain;

    @BeforeEach
    void setUp() {
        jwtUtil = mock(JwtUtil.class);
        filter = new JwtAuthenticationFilter(jwtUtil);
        chain = mock(WebFilterChain.class);
        when(chain.filter(any())).thenReturn(Mono.empty());
    }

    @Test
    void shouldSkipOptionsRequest() {
        var request = MockServerHttpRequest.options("/api/test").build();
        var exchange = MockServerWebExchange.from(request);
        filter.filter(exchange, chain).block();
        verify(chain).filter(exchange);
        verifyNoInteractions(jwtUtil);
    }

    @Test
    void shouldAuthenticateFromCookie() {
        var token = "valid-token";
        var request = MockServerHttpRequest.get("/api/test")
                .cookie(new HttpCookie("jwt", token))
                .build();
        var exchange = MockServerWebExchange.from(request);

        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn("admin");

        filter.filter(exchange, chain).block();

        verify(jwtUtil).validateToken(token);
        verify(jwtUtil).getUsernameFromToken(token);
        verify(chain).filter(exchange);
    }

    @Test
    void shouldAuthenticateFromAuthorizationHeader() {
        var token = "valid-token";
        var authHeader = "Bearer " + token;

        var request = MockServerHttpRequest.get("/api/test")
                .header(HttpHeaders.AUTHORIZATION, authHeader)
                .build();
        var exchange = MockServerWebExchange.from(request);

        when(jwtUtil.validateToken(token)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(token)).thenReturn("admin");

        filter.filter(exchange, chain).block();

        verify(jwtUtil).validateToken(token);
        verify(jwtUtil).getUsernameFromToken(token);
        verify(chain).filter(exchange);
    }

    @Test
    void shouldSkipInvalidToken() {
        var token = "invalid-token";
        var request = MockServerHttpRequest.get("/api/test")
                .cookie(new HttpCookie("jwt", token))
                .build();
        var exchange = MockServerWebExchange.from(request);

        when(jwtUtil.validateToken(token)).thenReturn(false);

        filter.filter(exchange, chain).block();

        verify(jwtUtil).validateToken(token);
        verify(chain).filter(exchange);
    }
}
