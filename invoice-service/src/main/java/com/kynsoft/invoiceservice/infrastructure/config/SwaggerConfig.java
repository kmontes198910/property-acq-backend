package com.kynsoft.invoiceservice.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

/**
 * Configuración de OpenAPI con anotaciones para Swagger UI
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API de Facturación",
                description = "API para la gestión de facturación electrónica",
                version = "1.0.0",
                contact = @Contact(
                        name = "Kynsoft",
                        email = "info@kynsoft.com",
                        url = "https://www.kynsoft.com"
                ),
                license = @License(
                        name = "Privativa",
                        url = "https://www.kynsoft.com/license"
                )
        ),
        security = @SecurityRequirement(name = "bearer-jwt"),
        servers = {
                @Server(url = "/", description = "Servidor local")
        }
)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearer-jwt",
                type = HTTP,
                scheme = "bearer",
                bearerFormat = "JWT",
                in = HEADER,
                description = "Token JWT de autenticación"
        )
})
public class SwaggerConfig {
    // Esta clase solo contiene anotaciones de configuración para OpenAPI
}
