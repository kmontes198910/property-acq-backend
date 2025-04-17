package com.kynsoft.gateway.infrastructure.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Filtro personalizado para extraer el ID de usuario del token JWT 
 * y añadirlo como encabezado a las solicitudes entrantes.
 * Se usa principalmente para permitir que los microservicios backend 
 * puedan recibir el ID de usuario sin depender directamente de Spring Security
 */
@Component
public class UserIdHeaderFilter extends AbstractGatewayFilterFactory<UserIdHeaderFilter.Config> {
    
    public UserIdHeaderFilter() {
        super(Config.class);
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            return exchange.getPrincipal()
                    .filter(principal -> principal instanceof JwtAuthenticationToken)
                    .cast(JwtAuthenticationToken.class)
                    .map(jwtAuth -> {
                        Jwt jwt = jwtAuth.getToken();
                        String userId = jwt.getClaim("sub");
                        
                        // Solo añadir el encabezado si se encontró un ID válido
                        if (userId != null && !userId.isEmpty()) {
                            exchange.getRequest().mutate()
                                    .header("X-User-Id", userId)
                                    .build();
                        }
                        return exchange;
                    })
                    .defaultIfEmpty(exchange)
                    .flatMap(chain::filter);
        };
    }
    
    public static class Config {
        // Clase de configuración vacía, ya que no necesitamos configuraciones adicionales
    }
}