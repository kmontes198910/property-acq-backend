package com.kynsof.identity.infrastructure.config.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebFilter implementation for rate limiting in a reactive environment.
 * This filter enforces rate limits based on client IP and the rate limit type.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitWebFilter implements WebFilter {

    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();
    private final Map<RateLimit.RateLimitType, Bandwidth> limitDefinitions;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        // First, find the handler for this request to check for @RateLimit annotation
        return requestMappingHandlerMapping.getHandler(exchange)
                .flatMap(handler -> {
                    // Check if handler is a method handler
                    if (!(handler instanceof HandlerMethod)) {
                        return chain.filter(exchange);
                    }
                    
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    RateLimit rateLimitAnnotation = AnnotationUtils.findAnnotation(
                            handlerMethod.getMethod(), RateLimit.class);
                    
                    // If no rate limit annotation, proceed with the chain
                    if (rateLimitAnnotation == null) {
                        return chain.filter(exchange);
                    }
                    
                    // Extract client IP and create bucket key
                    String clientIp = extractClientIp(request);
                    RateLimit.RateLimitType limitType = rateLimitAnnotation.type();
                    String bucketKey = clientIp + ":" + limitType.name();
                    
                    // Get or create a bucket for this IP and rate limit type
                    Bucket bucket = bucketCache.computeIfAbsent(bucketKey, 
                            key -> createNewBucket(limitType));
                    
                    // Try to consume a token from the bucket
                    ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
                    
                    if (probe.isConsumed()) {
                        // Rate limit not exceeded, proceed with the chain
                        return chain.filter(exchange);
                    } else {
                        // Rate limit exceeded, return 429 response
                        long waitForRefillSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000;
                        
                        log.warn("Rate limit exceeded for IP: {} on path: {}. Available in {} seconds", 
                                clientIp, request.getPath().value(), waitForRefillSeconds);
                        
                        // Build error response
                        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                        
                        String errorResponse = String.format(
                                "{\"status\":%d,\"error\":\"Too Many Requests\",\"message\":\"Rate limit exceeded. Try again in %d seconds.\"}",
                                HttpStatus.TOO_MANY_REQUESTS.value(), waitForRefillSeconds);
                        
                        return exchange.getResponse().writeWith(
                                Mono.just(exchange.getResponse().bufferFactory().wrap(errorResponse.getBytes())));
                    }
                })
                .switchIfEmpty(chain.filter(exchange)); // If no handler found, proceed with chain
    }

    /**
     * Creates a new bucket with the appropriate bandwidth limit
     * @param limitType the type of rate limit to apply
     * @return a new bucket configured with the appropriate limit
     */
    private Bucket createNewBucket(RateLimit.RateLimitType limitType) {
        // Get the appropriate bandwidth limit based on the rate limit type
        Bandwidth limit = Optional.ofNullable(limitDefinitions.get(limitType))
                .orElse(limitDefinitions.get(RateLimit.RateLimitType.DEFAULT));

        // Create and return a new bucket with the selected limit
        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    /**
     * Extracts the client IP address from the request
     * @param request The server HTTP request
     * @return The client IP address
     */
    private String extractClientIp(ServerHttpRequest request) {
        String clientIp = request.getHeaders().getFirst("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeaders().getFirst("HTTP_CLIENT_IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddress().getAddress().getHostAddress();
        }
        
        // If X-Forwarded-For has multiple IPs, take the first one (client IP)
        if (clientIp != null && clientIp.contains(",")) {
            clientIp = clientIp.split(",")[0].trim();
        }
        
        return clientIp;
    }
}

