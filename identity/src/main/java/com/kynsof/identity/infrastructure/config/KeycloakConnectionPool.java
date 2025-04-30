package com.kynsof.identity.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

// Importaciones actualizadas para compatibilidad con Jakarta EE
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Administra un pool de conexiones a Keycloak para mejorar el rendimiento y reducir
 * la sobrecarga de establecer nuevas conexiones en cada solicitud.
 */
@Component
@Slf4j
public class KeycloakConnectionPool {

    private final BlockingQueue<Keycloak> connectionPool;
    private final AtomicInteger activeConnections = new AtomicInteger(0);
    
    @Value("${keycloak.provider.server-url:https://auth.chevere.ddns.net/}")
    private String serverUrl;

    @Value("${keycloak.provider.realm-master:master}")
    private String realmMaster;

    @Value("${keycloak.provider.admin-clic:admin-cli}")
    private String adminCli;

    @Value("${keycloak.provider.user-console:admin}")
    private String userConsole;

    @Value("${keycloak.provider.password-console:ZWJjMTViM2U4YjQ0MTQwZTI5ZjI1YWFk}")
    private String passwordConsole;

    @Value("${keycloak.provider.client-secret:7i6w6w9yRbv2VOi0ksbLfdd1TnW5TTlb}")
    private String clientSecret;
    
    @Value("${keycloak.connection.pool.size:20}")
    private int poolSize;
    
    @Value("${keycloak.connection.timeout:10000}")
    private int connectionTimeout;
    
    @Value("${keycloak.connection.ttl:30}")
    private int connectionTTL;
    
    @Value("${keycloak.connection.pool.queue-size:50}")
    private int queueSize;
    
    @Value("${keycloak.connection.pool.max-wait-time:5}")
    private int maxWaitTime;
    
    @Value("${keycloak.connection.pool.enable-validation:true}")
    private boolean enableValidation;

    public KeycloakConnectionPool() {
        // Inicializar con un tamaño predeterminado, será reconfigurado en init()
        this.connectionPool = new ArrayBlockingQueue<>(50);
    }

    @PostConstruct
    public void init() {
        validateConfiguration();
        
        log.info("Inicializando pool de conexiones Keycloak con tamaño: {}", poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                Keycloak connection = createKeycloakClient();
                // Validar la conexión antes de agregarla al pool
                if (isConnectionValid(connection)) {
                    connectionPool.offer(connection);
                    activeConnections.incrementAndGet();
                    log.debug("Conexión #{} añadida al pool", i + 1);
                } else {
                    closeConnection(connection);
                    log.error("Conexión #{} no válida, no se agregó al pool", i + 1);
                }
            } catch (Exception e) {
                log.error("Error al inicializar conexión #{} de Keycloak: {}", i + 1, e.getMessage());
            }
        }
        log.info("Pool de conexiones Keycloak inicializado con {} conexiones activas", activeConnections.get());
    }
    
    private void validateConfiguration() {
        log.info("Validando configuración del pool de conexiones Keycloak");
        
        // Validar URLs
        if (serverUrl == null || serverUrl.isEmpty()) {
            throw new IllegalStateException("La URL del servidor Keycloak no puede estar vacía");
        }
        
        // Validar tamaños
        if (poolSize <= 0) {
            log.warn("Tamaño del pool inválido ({}). Estableciendo valor predeterminado: 10", poolSize);
            poolSize = 10;
        }
        
        if (queueSize < poolSize) {
            log.warn("Tamaño de cola ({}) menor que el tamaño del pool ({}). Ajustando a {}", 
                    queueSize, poolSize, poolSize * 2);
            queueSize = poolSize * 2;
        }
        
        log.info("Configuración del pool validada correctamente");
    }

    /**
     * Obtiene una conexión del pool. Si no hay conexiones disponibles,
     * espera hasta que una esté disponible o crea una nueva si no se alcanzó el límite.
     */
    public Keycloak getConnection() {
        Keycloak connection = null;
        try {
            // Intenta obtener una conexión del pool con timeout
            connection = connectionPool.poll(maxWaitTime, TimeUnit.SECONDS);
            
            // Si no hay conexiones disponibles y no se alcanzó el límite, crea una nueva
            if (connection == null) {
                int current = activeConnections.get();
                if (current < poolSize * 2) { // Permitir hasta el doble en picos de carga
                    log.warn("Pool de conexiones agotado. Creando conexión adicional #{}", current + 1);
                    connection = createKeycloakClient();
                    if (isConnectionValid(connection)) {
                        activeConnections.incrementAndGet();
                    } else {
                        log.error("La nueva conexión no es válida. Intentando obtener una del pool");
                        // Si la nueva conexión no es válida, intentar obtener una del pool
                        connection = connectionPool.poll(maxWaitTime, TimeUnit.SECONDS);
                    }
                } else {
                    log.error("Pool de conexiones saturado. Esperando por una conexión disponible");
                    connection = connectionPool.take(); // Bloqueante hasta que haya una disponible
                }
            }
            
            // Validar la conexión antes de devolverla
            if (connection != null && enableValidation && !isConnectionValid(connection)) {
                log.warn("Conexión obtenida no válida. Creando una nueva.");
                closeConnection(connection);
                connection = createKeycloakClient();
                if (!isConnectionValid(connection)) {
                    log.error("No se pudo crear una conexión válida");
                }
            }
            
            return connection;
        } catch (InterruptedException e) {
            log.error("Interrumpido mientras esperaba por una conexión", e);
            Thread.currentThread().interrupt();
            // Crear una conexión de emergencia en caso de interrupción
            return createKeycloakClient();
        }
    }

    /**
     * Devuelve una conexión al pool para ser reutilizada.
     */
    public void releaseConnection(Keycloak connection) {
        if (connection != null) {
            // Validar la conexión antes de regresarla al pool
            if (enableValidation && !isConnectionValid(connection)) {
                log.warn("Conexión no válida devuelta al pool. Cerrando y creando una nueva.");
                closeConnection(connection);
                
                // Intentar crear una nueva conexión para mantener el tamaño del pool
                try {
                    Keycloak newConnection = createKeycloakClient();
                    if (isConnectionValid(newConnection)) {
                        boolean returned = connectionPool.offer(newConnection);
                        if (!returned) {
                            closeConnection(newConnection);
                            activeConnections.decrementAndGet();
                        }
                    } else {
                        closeConnection(newConnection);
                        activeConnections.decrementAndGet();
                    }
                } catch (Exception e) {
                    log.error("Error al crear nueva conexión de reemplazo", e);
                    activeConnections.decrementAndGet();
                }
            } else {
                boolean returned = connectionPool.offer(connection);
                if (!returned) {
                    // Si el pool está lleno, cierra la conexión
                    log.warn("Pool de conexiones lleno. Cerrando conexión");
                    closeConnection(connection);
                    activeConnections.decrementAndGet();
                }
            }
        }
    }

    /**
     * Valida si una conexión de Keycloak está activa y funcionando correctamente.
     */
    private boolean isConnectionValid(Keycloak keycloak) {
        if (keycloak == null) {
            return false;
        }
        
        if (!enableValidation) {
            return true; // Si la validación está desactivada, asumir que es válida
        }
        
        try {
            // Intenta una operación simple para verificar la conexión
            keycloak.serverInfo().getInfo();
            return true;
        } catch (Exception e) {
            log.warn("Conexión a Keycloak no válida: {}", e.getMessage());
            return false;
        }
    }

    private Keycloak createKeycloakClient() {
        try {
            ResteasyClient resteasyClient = new ResteasyClientBuilderImpl()
                    .connectionPoolSize(5)
                    .connectTimeout(connectionTimeout, TimeUnit.MILLISECONDS)
                    .connectionTTL(connectionTTL, TimeUnit.MINUTES)
                    .disableTrustManager() // Solo para desarrollo, no usar en producción
                    .build();
                    
            return KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realmMaster)
                    .clientId(adminCli)
                    .username(userConsole)
                    .password(passwordConsole)
                    .clientSecret(clientSecret)
                    .resteasyClient(resteasyClient)
                    .build();
        } catch (Exception e) {
            log.error("Error al crear cliente Keycloak: {}", e.getMessage());
            throw new RuntimeException("No se pudo crear el cliente Keycloak", e);
        }
    }
    
    private void closeConnection(Keycloak keycloak) {
        if (keycloak != null) {
            try {
                keycloak.close();
                log.debug("Conexión Keycloak cerrada correctamente");
            } catch (Exception e) {
                log.error("Error al cerrar conexión Keycloak: {}", e.getMessage());
            }
        }
    }

    @PreDestroy
    public void shutdown() {
        log.info("Cerrando pool de conexiones Keycloak con {} conexiones", connectionPool.size());
        connectionPool.forEach(this::closeConnection);
        connectionPool.clear();
        log.info("Pool de conexiones Keycloak cerrado correctamente");
    }
    
    /**
     * Método para monitoreo y diagnóstico del estado actual del pool
     */
    public String getPoolStatus() {
        return String.format(
            "KeycloakConnectionPool - Conexiones activas: %d, Conexiones en el pool: %d, Tamaño máximo: %d",
            activeConnections.get(), connectionPool.size(), poolSize
        );
    }

    /**
     * Devuelve el número de conexiones activas (en uso)
     */
    public int getActiveConnectionCount() {
        return activeConnections.get() - connectionPool.size();
    }

    /**
     * Devuelve el número de conexiones disponibles en el pool
     */
    public int getAvailableConnectionCount() {
        return connectionPool.size();
    }

    /**
     * Limpia las conexiones inactivas después de un tiempo
     */
    @Scheduled(fixedRate = 600000) // Cada 10 minutos
    public void cleanupIdleConnections() {
        log.info("Iniciando limpieza de conexiones inactivas de Keycloak");
        
        int initialSize = connectionPool.size();
        int closedConnections = 0;
        
        // Crear una lista para almacenar conexiones a validar
        BlockingQueue<Keycloak> tempQueue = new ArrayBlockingQueue<>(connectionPool.size());
        Keycloak connection;
        
        // Sacar todas las conexiones del pool para validarlas
        while ((connection = connectionPool.poll()) != null) {
            if (enableValidation && !isConnectionValid(connection)) {
                // Si la conexión no es válida, cerrarla
                log.info("Cerrando conexión inválida");
                closeConnection(connection);
                activeConnections.decrementAndGet();
                closedConnections++;
            } else {
                // Si es válida, la añadimos a la cola temporal
                tempQueue.offer(connection);
            }
        }
        
        // Devolver las conexiones válidas al pool
        int returnedConnections = 0;
        while ((connection = tempQueue.poll()) != null) {
            connectionPool.offer(connection);
            returnedConnections++;
        }
        
        log.info("Limpieza completada: {} conexiones eliminadas, {} conexiones válidas devueltas al pool", 
                closedConnections, returnedConnections);
    }
}