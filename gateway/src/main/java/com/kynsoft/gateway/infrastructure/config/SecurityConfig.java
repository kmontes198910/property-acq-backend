package com.kynsoft.gateway.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String JWK_SET_URI = "https://sso.kynsoft.net/realms/medinec/protocol/openid-connect/certs";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .cors(cors -> cors.disable()) // Configurar CORS en application.properties
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
        				.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .pathMatchers(HttpMethod.POST,"/identity/api/auth/*").permitAll()
                        .pathMatchers(HttpMethod.POST,"/payments-confirm/*").permitAll()
                        .pathMatchers(HttpMethod.GET, "/identity/api/auth/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/swagger-ui.html", "/swagger-ui/**", "/v2/api-docs.yaml", "/v3/api-docs.yaml", "/v2/api-docs/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
                       // .pathMatchers(AUTH_WHITELIST).permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtDecoder(jwtDecoder()) // Usa validación local
                        )
                )
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        try {
            NimbusReactiveJwtDecoder jwtDecoder = (NimbusReactiveJwtDecoder)
                    ReactiveJwtDecoders.fromIssuerLocation("https://sso.kynsoft.net/realms/medinec");

            return token -> Mono.fromSupplier(() -> jwtDecoder)
                    .cache(Duration.ofMinutes(5))
                    .flatMap(decoder -> decoder.decode(token));

        } catch (Exception e) {
            throw new RuntimeException("No se pudo inicializar el JWT Decoder", e);
        }
    }
}