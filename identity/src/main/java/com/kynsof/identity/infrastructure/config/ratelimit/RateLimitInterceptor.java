package com.kynsof.identity.infrastructure.config.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();
    private final Map<RateLimit.RateLimitType, Bandwidth> limitDefinitions;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RateLimit rateLimitAnnotation = handlerMethod.getMethodAnnotation(RateLimit.class);

        if (rateLimitAnnotation == null) {
            return true;
        }

        String clientIp = extractClientIp(request);
        RateLimit.RateLimitType limitType = rateLimitAnnotation.type();
        String bucketKey = clientIp + ":" + limitType.name();

        Bucket bucket = bucketCache.computeIfAbsent(bucketKey, key -> createNewBucket(limitType));

        io.github.bucket4j.ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

        if (!probe.isConsumed()) {
            long waitForRefillSeconds = (long) Math.ceil(probe.getNanosToWaitForRefill() / 1_000_000_000.0);

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.setHeader("Retry-After", String.valueOf(waitForRefillSeconds));
            response.getWriter().write(String.format(
                    "{\"status\":%d,\"error\":\"Too Many Requests\",\"message\":\"Rate limit exceeded. Try again in %d seconds.\"}",
                    HttpStatus.TOO_MANY_REQUESTS.value(), waitForRefillSeconds));

            log.warn("Rate limit exceeded for IP: {} on path: {}. Available in {} seconds",
                    clientIp, request.getRequestURI(), waitForRefillSeconds);
            return false;
        }

        return true;
    }

    private Bucket createNewBucket(RateLimit.RateLimitType limitType) {
        Bandwidth limit = Optional.ofNullable(limitDefinitions.get(limitType))
                .orElse(limitDefinitions.get(RateLimit.RateLimitType.DEFAULT));

        return Bucket4j.builder().addLimit(limit).build();
    }

    private String extractClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        if (clientIp != null && clientIp.contains(",")) {
            clientIp = clientIp.split(",")[0].trim();
        }
        return clientIp;
    }
}