package com.kynsoft.invoiceservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kynsoft.invoiceservice.infrastructure.converters.AttributeEncryptor;
import com.kynsoft.invoiceservice.infrastructure.util.EncryptionUtil;

/**
 * Configuración para asegurar que los convertidores JPA se inicialicen correctamente
 */
@Configuration
public class JpaConvertersConfig {

    /**
     * Garantiza que el AttributeEncryptor se instancie dentro del contexto de Spring
     * antes de que JPA/Hibernate lo necesite
     */
    @Bean
    public AttributeEncryptor attributeEncryptor(EncryptionUtil encryptionUtil) {
        AttributeEncryptor encryptor = new AttributeEncryptor();
        encryptor.setEncryptionUtil(encryptionUtil);
        return encryptor;
    }
}
