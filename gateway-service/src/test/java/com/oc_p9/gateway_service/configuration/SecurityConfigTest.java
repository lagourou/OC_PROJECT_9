package com.oc_p9.gateway_service.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import reactor.core.publisher.Mono;

class SecurityConfigTest {

    @Test
    void unauthorizedEntryPointShouldSet401() {
        var config = new SecurityConfig(mock(JwtAuthenticationFilter.class));
        var entryPoint = config.unauthorizedEntryPoint();

        var exchange = mock(org.springframework.web.server.ServerWebExchange.class);
        var response = mock(org.springframework.http.server.reactive.ServerHttpResponse.class);
        when(exchange.getResponse()).thenReturn(response);
        when(response.setComplete()).thenReturn(Mono.empty());

        entryPoint.commence(exchange, null).block();
        verify(response).setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldConfigureSecurityChain() {
        var filter = mock(JwtAuthenticationFilter.class);
        var config = new SecurityConfig(filter);
        var http = ServerHttpSecurity.http();

        var chain = config.securityFilterChain(http);
        assertNotNull(chain);
    }
}
