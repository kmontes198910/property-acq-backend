package com.kynsoft.wamessaging.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${spring.application.name}")
    private String applicationName;
    
    @Value("${server.port:9701}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("WhatsApp Messaging API - Medinec")
                .description("API para el envío y gestión de mensajes a través de WhatsApp Business")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Kynsoft")
                    .email("soporte@kynsoft.com")
                    .url("https://www.kynsoft.com"))
                .license(new License()
                    .name("Licencia Privada")
                    .url("https://www.kynsoft.com/licenses")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:" + serverPort)
                    .description("Servidor de desarrollo local"),
                new Server()
                    .url("https://api.medinec.com/wa-messaging")
                    .description("Servidor de Producción")
            ));
    }
}