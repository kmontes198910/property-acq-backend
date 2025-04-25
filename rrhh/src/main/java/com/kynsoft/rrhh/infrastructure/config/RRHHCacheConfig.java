package com.kynsoft.rrhh.infrastructure.config;

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
public class RRHHCacheConfig {

    // Definición de constantes para los nombres de las cachés
    public static final String DOCTORS_CACHE = "doctors-cache";
    public static final String DOCTORS_BY_ID_CACHE = "doctors-by-id-cache";
    public static final String DOCTOR_SEARCH_CACHE = "doctor-search-cache";
    public static final String USERS_BY_ID_CACHE = "users-by-id-cache";
    public static final String USERS_BY_IDENTIFICATION_CACHE = "users-by-identification-cache";
    public static final String BUSINESS_CACHE = "business-cache";
    public static final String BUSINESS_BY_ID_CACHE = "business-by-id-cache";
    public static final String ASSISTANT_CACHE = "assistant-cache";
    public static final String ASSISTANT_BY_ID_CACHE = "assistant-by-id-cache";

    @Bean
    @Primary
    public CacheManager rhrhCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // Configuración para caché de doctores (1 hora ya que cambian poco)
        cacheConfigurations.put(DOCTORS_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
            
        // Configuración para caché de doctores por ID (1 hora)
        cacheConfigurations.put(DOCTORS_BY_ID_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
            
        // Configuración para caché de búsqueda de doctores (10 minutos porque puede cambiar con más frecuencia)
        cacheConfigurations.put(DOCTOR_SEARCH_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(10)));
            
        // Configuración para caché de usuarios por ID (1 hora)
        cacheConfigurations.put(USERS_BY_ID_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
            
        // Configuración para caché de usuarios por identificación (1 hora)
        cacheConfigurations.put(USERS_BY_IDENTIFICATION_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
            
        // Configuración para caché de negocios (1 hora)
        cacheConfigurations.put(BUSINESS_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
            
        // Configuración para caché de negocios por ID (1 hora)
        cacheConfigurations.put(BUSINESS_BY_ID_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
            
        // Configuración para caché de asistentes (1 hora)
        cacheConfigurations.put(ASSISTANT_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));
            
        // Configuración para caché de asistentes por ID (1 hora)
        cacheConfigurations.put(ASSISTANT_BY_ID_CACHE, 
            createCacheConfiguration(Duration.ofHours(1)));

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