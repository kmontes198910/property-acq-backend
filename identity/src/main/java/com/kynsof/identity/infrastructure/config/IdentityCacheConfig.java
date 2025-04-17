package com.kynsof.identity.infrastructure.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class IdentityCacheConfig {

    public static final String USER_INFO_CACHE = "user-info-cache";
    public static final String BUSINESS_CACHE = "business-cache";
    public static final String MODULE_CACHE = "module-cache";
    public static final String USER_SYSTEM_CACHE = "user-system-cache";
    public static final String USER_SYSTEM_EMAIL_CACHE = "user-system-email-cache";
    public static final String USER_EXISTS_CACHE = "user-exists-cache";

    @Bean
    @Primary
    public CacheManager identityCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // Configuración para información de usuario (30 minutos)
        cacheConfigurations.put(USER_INFO_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(30)));
        
        // Configuración para negocios (1 hora)
        cacheConfigurations.put(BUSINESS_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
        
        // Configuración para módulos (2 horas)
        cacheConfigurations.put(MODULE_CACHE, 
            createCacheConfiguration(Duration.ofHours(2)));
        
        // Configuración para sistema de usuarios (1 hora)
        cacheConfigurations.put(USER_SYSTEM_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
        
        // Configuración para cache por email (1 hora)
        cacheConfigurations.put(USER_SYSTEM_EMAIL_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
        
        // Configuración para verificación de existencia de usuarios (15 minutos)
        cacheConfigurations.put(USER_EXISTS_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(15)));

        return RedisCacheManager.builder(redisConnectionFactory)
            .withInitialCacheConfigurations(cacheConfigurations)
            .build();
    }

    private RedisCacheConfiguration createCacheConfiguration(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(ttl)
            .disableCachingNullValues();
    }
}