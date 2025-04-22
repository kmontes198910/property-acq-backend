package com.kynsoft.notification.infrastructure.config;

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

/**
 * Configuración de caché con Redis para el microservicio cloudBridges
 */
@Configuration
@EnableCaching
public class CloudBridgesCacheConfig {

    // Definición de constantes para los nombres de las cachés
    public static final String SECURE_FILE_CACHE = "secure-file-cache";
    public static final String FILE_INFO_CACHE = "file-info-cache";

    /**
     * Configuración del CacheManager con Redis
     * @param redisConnectionFactory La fábrica de conexiones Redis inyectada automáticamente
     * @return El CacheManager configurado
     */
    @Bean
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        
        // Configuración para archivos seguros (30 minutos)
        cacheConfigurations.put(SECURE_FILE_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(30)));
        
        // Configuración para información de archivos (15 minutos)
        cacheConfigurations.put(FILE_INFO_CACHE, 
            createCacheConfiguration(Duration.ofMinutes(15)));

        return RedisCacheManager.builder(redisConnectionFactory)
            .withInitialCacheConfigurations(cacheConfigurations)
            .build();
    }

    /**
     * Crea una configuración de caché con el tiempo de vida especificado
     * @param ttl Tiempo de vida (Time To Live) para los elementos en caché
     * @return Configuración de la caché
     */
    private RedisCacheConfiguration createCacheConfiguration(Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(ttl)
            .disableCachingNullValues();
    }
}