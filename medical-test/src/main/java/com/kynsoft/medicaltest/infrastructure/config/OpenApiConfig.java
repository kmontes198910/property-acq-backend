package com.kynsoft.medicaltest.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración para la documentación OpenAPI/Swagger
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API de Exámenes Médicos")
                        .description("API RESTful para la gestión de órdenes de exámenes médicos y resultados")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Kynsoft")
                                .email("contacto@kynsoft.com")
                                .url("https://www.kynsoft.com"))
                        .license(new License()
                                .name("Propietary")
                                .url("https://www.kynsoft.com/terms")))
                .servers(List.of(
                        new Server()
                                .url("/api/medical-test")
                                .description("API de Exámenes Médicos")
                ));
    }
}
