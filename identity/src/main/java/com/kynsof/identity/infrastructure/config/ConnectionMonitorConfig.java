package com.kynsof.identity.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class ConnectionMonitorConfig {

    @Autowired
    private HikariDataSource dataSource;
    
    @Autowired
    private KeycloakConnectionPool keycloakConnectionPool;

    @Scheduled(fixedRate = 300000) // Cada 5 minutos
    public void monitorDatabaseConnections() {
        log.info("Estado del pool de conexiones de BD: activas={}, inactivas={}, total={}",
                dataSource.getHikariPoolMXBean().getActiveConnections(),
                dataSource.getHikariPoolMXBean().getIdleConnections(),
                dataSource.getHikariPoolMXBean().getTotalConnections());
    }
    
    @Scheduled(fixedRate = 300000) // Cada 5 minutos
    public void monitorKeycloakConnections() {
        int activeConnections = keycloakConnectionPool.getActiveConnectionCount();
        int availableConnections = keycloakConnectionPool.getAvailableConnectionCount();
        int totalConnections = activeConnections + availableConnections;
        
        log.info("Estado del pool de conexiones de Keycloak: activas={}, disponibles={}, total={}",
                activeConnections, availableConnections, totalConnections);
    }
}