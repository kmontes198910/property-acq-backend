package com.kynsof.identity.infrastructure.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionHealthService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Ejecuta una consulta simple para verificar la salud de las conexiones y detectar posibles problemas
     */
    @Scheduled(fixedRate = 900000) // Cada 15 minutos
    public void checkConnectionHealth() {
        try {
            // Ejecutar consulta simple para verificar conexión
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            log.debug("Verificación de salud de conexión completada: {}", result);
        } catch (Exception e) {
            log.error("Error al verificar la salud de la conexión a la base de datos: {}", e.getMessage());
        }
    }
}