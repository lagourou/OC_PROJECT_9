package com.oc_p9.gateway_service.controller;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.oc_p9.gateway_service.util.JwtUtil;

import reactor.core.publisher.Mono;

@RestController
public class AuthController {

    private final JwtUtil jwtUtil;
    private final long validityInSeconds = 3600;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public static class AuthRequest {
        public String username;
        public String password;
    }

    public static class AuthResponse {
        public String token;

        public AuthResponse(String token) {
            this.token = token;
        }
    }

    @PostMapping(value = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest request) {
        if (isValidUser(request.username, request.password)) {
            String token = jwtUtil.generateToken(request.username);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(false)
                    .sameSite("Lax")
                    .path("/")
                    .maxAge(Duration.ofSeconds(validityInSeconds))
                    .build();

            return Mono.just(ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new AuthResponse(token)));
        } else {
            return Mono.just(ResponseEntity.status(401).build());
        }
    }

    @PostMapping("/auth/logout")
    public Mono<ResponseEntity<Void>> logout() {
        ResponseCookie expiredCookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        return Mono.just(ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, expiredCookie.toString())
                .build());
    }

    private boolean isValidUser(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }
}
