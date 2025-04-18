package com.kynsof.calendar.infrastructure.config;

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
public class CalendarCacheConfig {

    // Definición de constantes para los nombres de las cachés
    public static final String SERVICE_TYPE_CACHE = "service-type-cache";
    public static final String SCHEDULE_AVAILABILITY_CACHE = "schedule-availability-cache";
    public static final String BUSINESS_SERVICE_CACHE = "business-service-cache";
    public static final String RESOURCE_SERVICE_CACHE = "resource-service-cache";
    public static final String DASHBOARD_STATS_CACHE = "dashboard-stats-cache";
    public static final String BUSINESS_RESOURCE_CACHE = "business-resource-cache";
    public static final String SERVICE_CACHE = "service-cache";

    @Bean
    @Primary
    public CacheManager calendarCacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // Configuración para tipos de servicio (30 minutos ya que cambian poco)
        cacheConfigurations.put(SERVICE_TYPE_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(30)));

        cacheConfigurations.put(SERVICE_CACHE,
                createCacheConfiguration(Duration.ofMinutes(30)));
        
        // Configuración para disponibilidad de horarios (2 minutos porque cambia frecuentemente)
        cacheConfigurations.put(SCHEDULE_AVAILABILITY_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(2)));
        
        // Configuración para servicios por negocio (15 minutos)
        cacheConfigurations.put(BUSINESS_SERVICE_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(15)));
        
        // Configuración para servicios por recurso (15 minutos)
        cacheConfigurations.put(RESOURCE_SERVICE_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(15)));
        
        // Configuración para estadísticas del dashboard (5 minutos)
        cacheConfigurations.put(DASHBOARD_STATS_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(5)));
            
        // Configuración para recursos por negocio (15 minutos)
        cacheConfigurations.put(BUSINESS_RESOURCE_CACHE, 
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