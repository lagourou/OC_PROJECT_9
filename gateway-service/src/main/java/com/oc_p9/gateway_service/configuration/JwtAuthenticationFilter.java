package com.oc_p9.gateway_service.configuration;

import com.oc_p9.gateway_service.util.JwtUtil;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Filtre WebFlux pour authentifier les requêtes HTTP via un token JWT.
 * 
 * Le token peut être récupéré depuis un cookie nommé "jwt" ou depuis l'en-tête "Authorization".
 * S'il est valide, l'utilisateur est authentifié dans le contexte de sécurité.
 */
@Component
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Filtre les requêtes pour vérifier la présence et la validité d'un token JWT.
     * Si le token est valide, l'utilisateur est ajouté au contexte de sécurité.
     *
     * @param exchange L'objet contenant la requête et la réponse HTTP.
     * @param chain Le filtre suivant dans la chaîne.
     * @return Un objet Mono représentant l'exécution du filtre.
     */
    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        var request = exchange.getRequest();

        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return chain.filter(exchange);
        }

        String token = null;

        var cookie = request.getCookies().getFirst("jwt");
        if (cookie != null) {
            token = cookie.getValue();
        } else {
            List<String> authHeaders = request.getHeaders().getOrEmpty("Authorization");
            if (!authHeaders.isEmpty()) {
                String authHeader = authHeaders.get(0);
                if (authHeader.startsWith("Bearer ")) {
                    token = authHeader.substring(7);
                }
            }
        }

        if (token != null) {
            boolean valid = jwtUtil.validateToken(token);
            if (valid) {
                String username = jwtUtil.getUsernameFromToken(token);
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
            }
        }

        return chain.filter(exchange);
    }
}
