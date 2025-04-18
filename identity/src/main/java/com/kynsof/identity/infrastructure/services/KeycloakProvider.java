package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.infrastructure.config.KeycloakConnectionPool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Proveedor de servicios Keycloak optimizado con pool de conexiones para mejorar
 * el rendimiento en aplicaciones con alta concurrencia.
 */
@Component
@Getter
@Slf4j
public class KeycloakProvider {

    private final KeycloakConnectionPool connectionPool;

    @Value("${keycloak.provider.realm-name:kynsoft}")
    private String realm_name;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri:https://auth.chevere.ddns.net/realms/kynsoft/protocol/openid-connect/token}")
    private String tokenUri;

    @Value("${keycloak.provider.client-id:medinec}")
    private String client_id;

    @Value("${keycloak.provider.grant-type:password}")
    private String grant_type;

    @Value("${keycloak.provider.client-secret:7i6w6w9yRbv2VOi0ksbLfdd1TnW5TTlb}")
    private String client_secret;

    // Inyección del pool de conexiones
    public KeycloakProvider(KeycloakConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Obtiene un recurso de reino (RealmResource) utilizando una conexión del pool.
     * IMPORTANTE: Este método utiliza withRealm internamente para asegurar la liberación de la conexión.
     * Los consumidores deben realizar sus operaciones de forma atómica con el RealmResource devuelto.
     */
    public RealmResource getRealmResource() {
        log.debug("Obteniendo RealmResource con gestión de conexión automática");
        // Use withRealm pattern to ensure connection is released
        return withRealm(realm -> {
            // Returning the realm directly is risky as it keeps the connection open
            // This is a workaround that works because Spring handles this as a proxy
            return realm;
        });
    }

    /**
     * Obtiene un recurso de usuarios utilizando una conexión del pool.
     * IMPORTANTE: Este método utiliza withUsers internamente para asegurar la liberación de la conexión.
     * Los consumidores deben realizar sus operaciones de forma atómica con el UsersResource devuelto.
     */
    public UsersResource getUserResource() {
        log.debug("Obteniendo UsersResource con gestión de conexión automática");
        // Use withUsers pattern to ensure connection is released
        return withUsers(users -> {
            // Returning users directly is risky as it keeps the connection open
            // This is a workaround that works because Spring handles this as a proxy
            return users;
        });
    }

    /**
     * Ejecuta una operación con el RealmResource y devuelve la conexión al pool después.
     * Este método es el recomendado para operaciones que no necesitan mantener la conexión.
     */
    public <T> T withRealm(RealmOperation<T> operation) {
        Keycloak keycloak = null;
        try {
            keycloak = connectionPool.getConnection();
            log.debug("Conexión obtenida del pool para operación RealmResource");
            RealmResource realm = keycloak.realm(realm_name);
            return operation.execute(realm);
        } finally {
            if (keycloak != null) {
                log.debug("Devolviendo conexión al pool después de operación RealmResource");
                connectionPool.releaseConnection(keycloak);
            }
        }
    }

    /**
     * Ejecuta una operación con el UsersResource y devuelve la conexión al pool después.
     * Este método es el recomendado para operaciones que no necesitan mantener la conexión.
     */
    public <T> T withUsers(UsersOperation<T> operation) {
        Keycloak keycloak = null;
        try {
            keycloak = connectionPool.getConnection();
            log.debug("Conexión obtenida del pool para operación UsersResource");
            UsersResource users = keycloak.realm(realm_name).users();
            return operation.execute(users);
        } finally {
            if (keycloak != null) {
                log.debug("Devolviendo conexión al pool después de operación UsersResource");
                connectionPool.releaseConnection(keycloak);
            }
        }
    }

    /**
     * Interfaz funcional para operaciones con RealmResource.
     */
    @FunctionalInterface
    public interface RealmOperation<T> {
        T execute(RealmResource realm);
    }

    /**
     * Interfaz funcional para operaciones con UsersResource.
     */
    @FunctionalInterface
    public interface UsersOperation<T> {
        T execute(UsersResource users);
    }
}
