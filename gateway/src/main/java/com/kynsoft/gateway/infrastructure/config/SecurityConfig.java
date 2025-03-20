package com.kynsoft.gateway.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String REQUIRED_HEADER = "X-Client-Token";

    @Value("${gateway.security.expected-header-value:pQfoROQs2QG0WuXwLvuCHocprzq87w774sF5XtVhuMU}")
    private String expectedHeaderValue;

    @Value("${gateway.security.enable-header-validation:true}")
    private boolean enableHeaderValidation;

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
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder()))
                )
                .addFilterAt((exchange, chain) -> validateHeader(exchange)
                                .flatMap(valid -> valid ? chain.filter(exchange) : exchange.getResponse().setComplete()),
                        SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    private Mono<Boolean> validateHeader(ServerWebExchange exchange) {
        if (!enableHeaderValidation) {
            return Mono.just(true); // Si la validación está deshabilitada, siempre permite la solicitud
        }

        boolean isValid = exchange.getRequest().getHeaders().containsKey(REQUIRED_HEADER) &&
                expectedHeaderValue.equals(exchange.getRequest().getHeaders().getFirst(REQUIRED_HEADER));

        if (!isValid) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        }

        return Mono.just(isValid);
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