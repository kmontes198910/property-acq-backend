package com.kynsoft.invoiceservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Configuración personalizada para el manejo de transacciones.
 * Define timeouts y comportamientos para las transacciones en la aplicación.
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    /**
     * Configura un TransactionTemplate con un timeout extendido para operaciones
     * que requieran más tiempo durante depuración o desarrollo.
     * 
     * @param transactionManager El gestor de transacciones de Spring
     * @return Un TransactionTemplate configurado
     */
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        // Para desarrollo, se configura un timeout más alto (3 minutos)
        // En producción, se recomienda un valor menor (30-60 segundos)
        template.setTimeout(180); // 180 segundos (3 minutos) para facilitar el debugging
        return template;
    }
}
