package com.kynsoft.finamer.digitalsignature.infrastructure.config;

// import io.swagger.v3.oas.models.Components;
// import io.swagger.v3.oas.models.OpenAPI;
// import io.swagger.v3.oas.models.info.Contact;
// import io.swagger.v3.oas.models.info.Info;
// import io.swagger.v3.oas.models.info.License;
// import io.swagger.v3.oas.models.security.SecurityRequirement;
// import io.swagger.v3.oas.models.security.SecurityScheme;
// import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Configuration;

// import java.util.Arrays;

/**
 * Configuración personalizada para Swagger/OpenAPI.
 * Desactivada temporalmente debido a problemas de compatibilidad.
 */
@Configuration
public class OpenApiConfig {

    /*
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Firma Digital")
                        .description("API para la firma digital de documentos PDF con certificados digitales")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Kynsoft")
                                .url("https://medinec.com")
                                .email("soporte@kynsoft.com"))
                        .license(new License()
                                .name("Licencia Propietaria")
                                .url("https://medinec.com/terms")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createBearerScheme()))
                .tags(Arrays.asList(
                        new Tag().name("Firma Digital").description("Operaciones de firma digital de documentos"),
                        new Tag().name("Validación").description("Operaciones de validación de firmas digitales")
                ));
    }

    private SecurityScheme createBearerScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
    }
    */
}