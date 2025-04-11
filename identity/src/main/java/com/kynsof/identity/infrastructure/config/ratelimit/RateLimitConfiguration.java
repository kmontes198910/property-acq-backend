package com.kynsof.identity.infrastructure.config.ratelimit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimitConfiguration {

    private static final int LOGIN_CAPACITY = 5;
    private static final int DEFAULT_CAPACITY = 10;
    private static final int PASSWORD_RECOVERY_CAPACITY = 3;
    private static final int PASSWORD_CHANGE_CAPACITY = 2;

    private static final Duration ONE_MINUTE = Duration.ofMinutes(1);

    @Bean
    public Map<RateLimit.RateLimitType, Bandwidth> limitDefinitions() {
        Map<RateLimit.RateLimitType, Bandwidth> limits = new ConcurrentHashMap<>();

        limits.put(RateLimit.RateLimitType.LOGIN,
                Bandwidth.classic(LOGIN_CAPACITY, Refill.intervally(LOGIN_CAPACITY, ONE_MINUTE)));

        limits.put(RateLimit.RateLimitType.DEFAULT,
                Bandwidth.classic(DEFAULT_CAPACITY, Refill.intervally(DEFAULT_CAPACITY, ONE_MINUTE)));

        limits.put(RateLimit.RateLimitType.PASSWORD_RECOVERY,
                Bandwidth.classic(PASSWORD_RECOVERY_CAPACITY, Refill.intervally(PASSWORD_RECOVERY_CAPACITY, ONE_MINUTE)));

        limits.put(RateLimit.RateLimitType.PASSWORD_CHANGE,
                Bandwidth.classic(PASSWORD_CHANGE_CAPACITY, Refill.intervally(PASSWORD_CHANGE_CAPACITY, ONE_MINUTE)));

        return limits;
    }
}