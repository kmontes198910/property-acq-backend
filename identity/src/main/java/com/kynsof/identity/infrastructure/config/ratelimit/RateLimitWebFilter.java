package com.kynsof.identity.infrastructure.config.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class RateLimitWebFilter implements WebFilter {

    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();
    private final Map<RateLimit.RateLimitType, Bandwidth> limitDefinitions;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        RateLimit.RateLimitType limitType = determineLimitType(exchange);

        String clientIp = extractClientIp(request);
        String bucketKey = clientIp + ":" + limitType.name();

        Bucket bucket = bucketCache.computeIfAbsent(bucketKey, key -> createNewBucket(limitType));
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (!probe.isConsumed()) {
            long waitForRefillSeconds = (long) Math.ceil(probe.getNanosToWaitForRefill() / 1_000_000_000.0);

            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            exchange.getResponse().getHeaders().add("Retry-After", String.valueOf(waitForRefillSeconds));

            String json = String.format(
                    "{\"status\":429,\"error\":\"Too Many Requests\",\"message\":\"Rate limit exceeded. Try again in %d seconds.\"}",
                    waitForRefillSeconds
            );

            DataBuffer buffer = exchange.getResponse().bufferFactory()
                    .wrap(json.getBytes(StandardCharsets.UTF_8));

            log.warn("Rate limit exceeded for IP: {} on path: {}. Available in {} seconds",
                    clientIp, request.getURI().getPath(), waitForRefillSeconds);

            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        return chain.filter(exchange);
    }

    private Bucket createNewBucket(RateLimit.RateLimitType limitType) {
        Bandwidth limit = Optional.ofNullable(limitDefinitions.get(limitType))
                .orElse(limitDefinitions.get(RateLimit.RateLimitType.DEFAULT));

        return Bucket4j.builder().addLimit(limit).build();
    }

    private String extractClientIp(ServerHttpRequest request) {
        return Optional.ofNullable(request.getHeaders().getFirst("X-Forwarded-For"))
                .map(ip -> ip.contains(",") ? ip.split(",")[0].trim() : ip)
                .orElseGet(() -> {
                    if (request.getRemoteAddress() != null && request.getRemoteAddress().getAddress() != null) {
                        return request.getRemoteAddress().getAddress().getHostAddress();
                    }
                    return "unknown";
                });
    }

    private RateLimit.RateLimitType determineLimitType(ServerWebExchange exchange) {
        String path = exchange.getRequest().getURI().getPath();

        if (path.contains("/authenticate")) return RateLimit.RateLimitType.LOGIN;
        if (path.contains("/exist-by-email")) return RateLimit.RateLimitType.LOGIN;
        if (path.contains("/forgot-password")) return RateLimit.RateLimitType.PASSWORD_RECOVERY;
        if (path.contains("/change-password") || path.contains("/firsts-change-password"))
            return RateLimit.RateLimitType.PASSWORD_CHANGE;

        return RateLimit.RateLimitType.DEFAULT;
    }
}