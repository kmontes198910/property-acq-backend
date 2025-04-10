package com.kynsof.identity.infrastructure.config.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimitConfiguration {

    /**
     * Creates a local bucket for rate limiting
     */
    @Bean
    public Bucket localBucket() {
        // Default limit: 10 requests per minute
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1)));
        
        // Bucket4j creates thread-safe buckets by default
        return io.github.bucket4j.Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
    /**
     * Defines bandwidth limits for different rate limit types
     */
    @Bean
    public Map<RateLimit.RateLimitType, Bandwidth> limitDefinitions() {
        Map<RateLimit.RateLimitType, Bandwidth> limits = new ConcurrentHashMap<>();
        
        // Default: 10 requests per minute
        limits.put(RateLimit.RateLimitType.DEFAULT, 
                Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))));
        
        // Login: 5 requests per minute
        limits.put(RateLimit.RateLimitType.LOGIN, 
                Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1))));
        
        // Password recovery: 3 requests per minute
        limits.put(RateLimit.RateLimitType.PASSWORD_RECOVERY, 
                Bandwidth.classic(3, Refill.intervally(3, Duration.ofMinutes(1))));
        
        // Password change: 2 requests per minute 
        limits.put(RateLimit.RateLimitType.PASSWORD_CHANGE, 
                Bandwidth.classic(2, Refill.intervally(2, Duration.ofMinutes(1))));
        
        return limits;
    }
}

