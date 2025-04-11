package com.kynsof.identity.infrastructure.config.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Configuration for rate limiting in the application.
 * 
 * This class defines the rate limits for different types of operations
 * using the Bucket4j token bucket algorithm. Each operation type has a
 * specific limit on the number of requests allowed within a time period.
 */
@Configuration
public class RateLimitConfiguration {

    // Constants for rate limit capacities
    private static final int LOGIN_CAPACITY = 5;
    private static final int DEFAULT_CAPACITY = 10;
    private static final int PASSWORD_RECOVERY_CAPACITY = 3;
    private static final int PASSWORD_CHANGE_CAPACITY = 2;
    
    // Duration constants
    private static final Duration ONE_MINUTE = Duration.ofMinutes(1);
    
    /**
     * Defines bandwidth limits for different rate limit types.
     * 
     * @return A map of rate limit types to their corresponding bandwidth limits
     */
    @Bean
    public Map<RateLimit.RateLimitType, Bandwidth> limitDefinitions() {
        Map<RateLimit.RateLimitType, Bandwidth> limits = new ConcurrentHashMap<>();
        
        // Login: exactly 5 requests per minute, using interval refill for strict control
        limits.put(RateLimit.RateLimitType.LOGIN, 
                Bandwidth.classic(LOGIN_CAPACITY, Refill.intervally(LOGIN_CAPACITY, ONE_MINUTE)));
        
        // Default: 10 requests per minute
        limits.put(RateLimit.RateLimitType.DEFAULT, 
                Bandwidth.classic(DEFAULT_CAPACITY, Refill.intervally(DEFAULT_CAPACITY, ONE_MINUTE)));
        
        // Password recovery: 3 requests per minute
        limits.put(RateLimit.RateLimitType.PASSWORD_RECOVERY, 
                Bandwidth.classic(PASSWORD_RECOVERY_CAPACITY, Refill.intervally(PASSWORD_RECOVERY_CAPACITY, ONE_MINUTE)));
        
        // Password change: 2 requests per minute 
        limits.put(RateLimit.RateLimitType.PASSWORD_CHANGE, 
                Bandwidth.classic(PASSWORD_CHANGE_CAPACITY, Refill.intervally(PASSWORD_CHANGE_CAPACITY, ONE_MINUTE)));
        
        return limits;
    }
}

