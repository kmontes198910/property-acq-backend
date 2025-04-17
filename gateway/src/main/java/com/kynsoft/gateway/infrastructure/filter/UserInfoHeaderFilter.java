package com.kynsoft.gateway.infrastructure.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Filtro global que extrae el ID del usuario y otros atributos relevantes del token JWT 
 * y los añade como encabezados a todas las solicitudes entrantes.
 * Esto permite que los microservicios backend no necesiten depender directamente de Spring Security
 * para obtener información del usuario autenticado.
 */
@Component
@Slf4j
public class UserInfoHeaderFilter implements GlobalFilter, Ordered {
    
    // Headers que se añadirán a las peticiones
    private static final String USER_ID_HEADER = "X-User-Id";
    private static final String USER_NAME_HEADER = "X-User-Name";
    private static final String USER_EMAIL_HEADER = "X-User-Email";
    private static final String USER_ROLES_HEADER = "X-User-Roles";
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .filter(securityContext -> securityContext.getAuthentication() != null)
                .map(SecurityContext::getAuthentication)
                .filter(auth -> auth instanceof JwtAuthenticationToken)
                .cast(JwtAuthenticationToken.class)
                .map(jwtAuth -> {
                    Jwt jwt = jwtAuth.getToken();
                    ServerHttpRequest request = addJwtClaimsAsHeaders(exchange.getRequest(), jwt, jwtAuth);
                    return exchange.mutate().request(request).build();
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }
    
    private ServerHttpRequest addJwtClaimsAsHeaders(ServerHttpRequest request, Jwt jwt, Authentication auth) {
        ServerHttpRequest.Builder builder = request.mutate();
        
        try {
            // Añadir el ID del usuario (sub) como header
            try {
                Object userIdObj = jwt.getClaim("sub");
                String userId = userIdObj != null ? userIdObj.toString() : "";
                if (userId != null && !userId.isEmpty()) {
                    builder.header(USER_ID_HEADER, userId);
                    log.debug("Añadido {} header: {}", USER_ID_HEADER, userId);
                }
            } catch (Exception e) {
                log.warn("Error al procesar sub del token: {}", e.getMessage());
            }
            
            // Añadir el nombre de usuario si está disponible
            try {
                Object usernameObj = jwt.getClaim("preferred_username");
                if (usernameObj != null) {
                    String username = usernameObj.toString();
                    if (!username.isEmpty()) {
                        builder.header(USER_NAME_HEADER, username);
                        log.debug("Añadido {} header: {}", USER_NAME_HEADER, username);
                    }
                }
            } catch (Exception e) {
                log.warn("Error al procesar preferred_username del token: {}", e.getMessage());
            }
            
            // Añadir el email si está disponible
            try {
                Object emailObj = jwt.getClaim("email");
                if (emailObj != null) {
                    String email = emailObj.toString();
                    if (!email.isEmpty()) {
                        builder.header(USER_EMAIL_HEADER, email);
                        log.debug("Añadido {} header: {}", USER_EMAIL_HEADER, email);
                    }
                }
            } catch (Exception e) {
                log.warn("Error al procesar email del token: {}", e.getMessage());
            }
            
            // Añadir roles si están disponibles
            if (auth.getAuthorities() != null && !auth.getAuthorities().isEmpty()) {
                try {
                    String roles = auth.getAuthorities().stream()
                            .filter(authority -> authority != null)
                            .map(authority -> {
                                try {
                                    return authority.toString();
                                } catch (Exception e) {
                                    log.warn("Error al convertir autoridad a string", e);
                                    return "";
                                }
                            })
                            .filter(role -> !role.isEmpty())
                            .reduce((a, b) -> a + "," + b)
                            .orElse("");
                    
                    if (!roles.isEmpty()) {
                        builder.header(USER_ROLES_HEADER, roles);
                        log.debug("Añadido {} header: {}", USER_ROLES_HEADER, roles);
                    }
                } catch (Exception e) {
                    log.warn("Error al procesar roles: {}", e.getMessage());
                }
            }
        } catch (Exception e) {
            log.error("Error al extraer información del token JWT: {}", e.getMessage());
        }
        
        return builder.build();
    }
    
    @Override
    public int getOrder() {
        // Alta prioridad para que se ejecute antes que otros filtros
        return Ordered.HIGHEST_PRECEDENCE + 100;
    }
}