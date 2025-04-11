package com.kynsof.identity.infrastructure.config.ratelimit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    RateLimitType type() default RateLimitType.DEFAULT;

    enum RateLimitType {
        DEFAULT,
        LOGIN,
        PASSWORD_RECOVERY,
        PASSWORD_CHANGE
    }
}