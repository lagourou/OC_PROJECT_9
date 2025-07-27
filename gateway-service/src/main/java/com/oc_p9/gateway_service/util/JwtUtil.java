package com.oc_p9.gateway_service.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * Classe pour gérer les opérations liées au JWT.
 * Elle permet de créer, valider et lire les informations d'un token.
 */
@Component
public class JwtUtil {

    private final Key secretKey;
    private final long jwtExpirationMs = 3600000; // Durée de validité du token en millisecondes (1h)

    /**
     * Initialise la clé secrète utilisée pour signer les tokens.
     *
     * @param secret la clé secrète définie dans le fichier de configuration
     */
    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Génère un token JWT pour un utilisateur.
     *
     * @param username le nom de l'utilisateur
     * @return un token signé avec une date de création et une date d’expiration
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date()) // date de création du token
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // expiration dans 1h
                .signWith(secretKey, SignatureAlgorithm.HS256) // algorithme de signature
                .compact();
    }

    /**
     * Vérifie si un token est valide.
     *
     * @param token le token à vérifier
     * @return vrai si le token est bien formé et non expiré, sinon faux
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Extrait le nom d’utilisateur contenu dans le token.
     *
     * @param token le token JWT
     * @return le nom de l’utilisateur (username)
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
