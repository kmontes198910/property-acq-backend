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

    // Store buckets in a concurrent map, keyed by IP + rate limit type
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

        // Get or create a bucket for this IP and rate limit type
        Bucket bucket = bucketCache.computeIfAbsent(bucketKey, key -> createNewBucket(limitType));
        
        // Use the API to check if consumption is possible and get timing information
        io.github.bucket4j.ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (!probe.isConsumed()) {
            // Convert nanoseconds to seconds for more user-friendly message
            long waitForRefillSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000;
            
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            response.getWriter().write(String.format(
                "{\"status\":%d,\"error\":\"Too Many Requests\",\"message\":\"Rate limit exceeded. Try again in %d seconds.\"}",
                HttpStatus.TOO_MANY_REQUESTS.value(), waitForRefillSeconds));
                
            log.warn("Rate limit exceeded for IP: {} on path: {}. Available in {} seconds", 
                     clientIp, request.getRequestURI(), waitForRefillSeconds);
            return false;
        }

        return true;
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

    private String extractClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
        
        // If X-Forwarded-For has multiple IPs, take the first one (client IP)
        if (clientIp != null && clientIp.contains(",")) {
            clientIp = clientIp.split(",")[0].trim();
        }
        
        return clientIp;
    }
}

