package com.kynsoft.invoiceservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:encryption.properties")
public class EncryptionConfig {
    // La configuración se carga desde el archivo encryption.properties
}
