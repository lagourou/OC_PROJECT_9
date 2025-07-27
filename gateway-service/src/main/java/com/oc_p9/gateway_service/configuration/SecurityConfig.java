package com.oc_p9.gateway_service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;

/**
 * Configuration de la sécurité pour la gateway
 * Utilise Spring Security avec WebFlux pour gérer les règles de sécurité.
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    /**
     * Constructeur qui injecte le filtre JWT.
     *
     * @param jwtFilter Filtre utilisé pour valider les tokens JWT
     */
    public SecurityConfig(JwtAuthenticationFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Définit le comportement quand un utilisateur non authentifié tente d'accéder à une ressource protégée.
     *
     * @return une réponse 401 si l'utilisateur n'est pas autorisé.
     */
    @Bean
    public ServerAuthenticationEntryPoint unauthorizedEntryPoint() {
        return (exchange, ex) -> {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        };
    }

    /**
     * Configure les règles de sécurité pour les routes HTTP.
     *
     * @param http Objet de configuration HTTP fourni par Spring Security
     */
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .exceptionHandling(e -> e.authenticationEntryPoint(unauthorizedEntryPoint()))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers("/auth/**").permitAll()
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/api/**").authenticated())
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

}
