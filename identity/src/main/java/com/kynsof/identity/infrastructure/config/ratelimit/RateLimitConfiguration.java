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
     * Defines bandwidth limits for different rate limit types
     */
    @Bean
    public Map<RateLimit.RateLimitType, Bandwidth> limitDefinitions() {
        Map<RateLimit.RateLimitType, Bandwidth> limits = new ConcurrentHashMap<>();
        
        // Login: exactly 5 requests per minute, using simple refill for strict control
        limits.put(RateLimit.RateLimitType.LOGIN, 
                Bandwidth.simple(25, Duration.ofMinutes(1)));
        
        // Default: 10 requests per minute
        limits.put(RateLimit.RateLimitType.DEFAULT, 
                Bandwidth.classic(10, Refill.intervally(10, Duration.ofMinutes(1))));
        
        // Password recovery: 3 requests per minute
        limits.put(RateLimit.RateLimitType.PASSWORD_RECOVERY, 
                Bandwidth.classic(3, Refill.intervally(3, Duration.ofMinutes(1))));
        
        // Password change: 2 requests per minute 
        limits.put(RateLimit.RateLimitType.PASSWORD_CHANGE, 
                Bandwidth.classic(2, Refill.intervally(2, Duration.ofMinutes(1))));
        
        return limits;
    }
}

