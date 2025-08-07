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

/**
 * Contrôleur d'authentification pour gérer la connexion et la déconnexion.
 */
@RestController
public class AuthController {

    private final JwtUtil jwtUtil;
    private final long validityInSeconds = 3600;

    /**
     * Crée un nouveau contrôleur avec l'outil JWT.
     *
     * @param jwtUtil outil pour générer les tokens JWT
     */
    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Représente une requête de connexion avec nom d'utilisateur et mot de passe.
     */
    public static class AuthRequest {
        public String username;
        public String password;
    }

    /**
     * Réponse envoyée après une connexion réussie, contenant le token JWT.
     */
    public static class AuthResponse {
        public String token;

        public AuthResponse(String token) {
            this.token = token;
        }
    }

    /**
     * Endpoint POST pour se connecter.
     * Vérifie les identifiants, génère un token JWT et le stocke dans un cookie
     * httpOnly.
     *
     * @param request les identifiants de l'utilisateur
     * @return réponse avec le token JWT ou erreur 401 si les identifiants sont
     *         invalides
     */
    @PostMapping(value = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest request) {
        if (isValidUser(request.username, request.password)) {
            String token = jwtUtil.generateToken(request.username);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true) // Cookie réserver qu'au échange Http (requêtes/réponses)
                    .secure(false)// Le cookie est envoyé même sans HTTPS
                    .sameSite("Lax")// Le cookie est envoyé seulement dans des cas sûrs
                    .path("/") // Le cookie est accessible sur toutes les routes
                    .maxAge(Duration.ofSeconds(validityInSeconds))
                    .build();

            return Mono.just(ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new AuthResponse(token)));
        } else {
            return Mono.just(ResponseEntity.status(401).build());
        }
    }

    /**
     * Endpoint POST pour se déconnecter.
     * Supprime le cookie JWT en le remplaçant par un cookie expiré.
     *
     * @return réponse vide avec cookie supprimé
     */
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

    /**
     * Vérifie si l'utilisateur est valide.
     * Ici, seul "admin" avec mot de passe "password" est accepté.
     *
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return true si valide, false sinon
     */
    private boolean isValidUser(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }
}
