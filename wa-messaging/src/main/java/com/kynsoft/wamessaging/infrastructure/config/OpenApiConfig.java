package com.kynsoft.wamessaging.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de Swagger/OpenAPI para la documentación de la API
 */
@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * Configuración principal de OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WhatsApp Messaging API")
                        .description("API para el envío y gestión de mensajes a través de WhatsApp Business")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Kynsoft")
                                .email("info@kynsoft.com")
                                .url("https://kynsoft.com"))
                        .license(new License()
                                .name("Privado")
                                .url("https://kynsoft.com/licencia")))
                .servers(List.of(
                        new Server()
                                .url("/")
                                .description("Servidor actual"),
                        new Server()
                                .url("https://api.medinec.com/wa-messaging")
                                .description("Servidor de producción")))
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
