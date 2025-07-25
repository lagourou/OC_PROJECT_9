package com.oc_p9.gateway_service.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil("mysecretkeymysecretkeymysecretkeymysecretkey");
    }

    @Test
    void shouldGenerateAndValidateToken() {
        String username = "admin";
        String token = jwtUtil.generateToken(username);

        assertTrue(jwtUtil.validateToken(token));
        assertEquals(username, jwtUtil.getUsernameFromToken(token));
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        String invalid = "invalid.token.value";
        assertFalse(jwtUtil.validateToken(invalid));
    }
}

