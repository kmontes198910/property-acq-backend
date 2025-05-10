package com.kynsoft.invoiceservice.infrastructure.config;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class ConnectionMonitorConfig {

    @Autowired
    @Qualifier("writeDataSource")
    private HikariDataSource writeDataSource;
    
    @Autowired
    @Qualifier("readDataSource")
    private HikariDataSource readDataSource;

    @Scheduled(fixedRate = 60000) // Cada 1 minuto
    public void monitorDatabaseConnections() {
        // Monitoreo del pool de escritura
        HikariPoolMXBean writePoolMXBean = writeDataSource.getHikariPoolMXBean();
        log.info("Estado del pool de conexiones de escritura: activas={}, inactivas={}, total={}",
                writePoolMXBean.getActiveConnections(),
                writePoolMXBean.getIdleConnections(),
                writePoolMXBean.getTotalConnections());
        
        // Monitoreo del pool de lectura
        HikariPoolMXBean readPoolMXBean = readDataSource.getHikariPoolMXBean();
        log.info("Estado del pool de conexiones de lectura: activas={}, inactivas={}, total={}",
                readPoolMXBean.getActiveConnections(),
                readPoolMXBean.getIdleConnections(),
                readPoolMXBean.getTotalConnections());
    }
    
    /**
     * Limpieza programada cada 5 minutos para conexiones inactivas
     */
    @Scheduled(fixedRate = 300000) // Cada 5 minutos
    public void cleanupIdleConnections() {
        log.info("Iniciando limpieza de conexiones inactivas");
        try {
            // Realizar limpieza de conexiones inactivas en ambos pools
            writeDataSource.getHikariPoolMXBean().softEvictConnections();
            readDataSource.getHikariPoolMXBean().softEvictConnections();
            log.info("Limpieza de conexiones inactivas completada");
        } catch (Exception e) {
            log.error("Error en la limpieza de conexiones: {}", e.getMessage());
        }
    }
}