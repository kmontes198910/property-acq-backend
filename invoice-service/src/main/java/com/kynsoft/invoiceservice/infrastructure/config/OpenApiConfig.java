package com.kynsoft.invoiceservice.infrastructure.config;

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

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Facturación - Medinec")
                .description("API para la generación y gestión de facturas electrónicas")
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
                    .url("/")
                    .description("Servidor actual"),
                new Server()
                    .url("https://api.medinec.com/invoice-service")
                    .description("Servidor de Producción")
            ));
    }
}