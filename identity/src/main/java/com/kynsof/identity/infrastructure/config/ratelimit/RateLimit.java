package com.kynsof.identity.infrastructure.config.ratelimit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark endpoints that should be rate limited.
 * The rate limit is applied based on the type of operation.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    
    /**
     * Type of rate limiting to apply
     */
    RateLimitType type() default RateLimitType.DEFAULT;
    
    /**
     * Available types of rate limiting
     */
    enum RateLimitType {
        // Default rate limiting (10 requests per minute)
        DEFAULT,
        // Login attempts (5 requests per minute)
        LOGIN,
        // Password recovery attempts (3 requests per minute)
        PASSWORD_RECOVERY,
        // Password change attempts (2 requests per minute)
        PASSWORD_CHANGE
    }
}

