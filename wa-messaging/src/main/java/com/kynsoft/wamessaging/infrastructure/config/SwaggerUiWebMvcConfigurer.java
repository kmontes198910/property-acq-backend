package com.kynsoft.wamessaging.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración adicional para asegurar que Swagger UI funcione correctamente
 */
@Configuration
public class SwaggerUiWebMvcConfigurer implements WebMvcConfigurer {

    private final String baseUrl;

    public SwaggerUiWebMvcConfigurer(
            @Value("${springdoc.api-docs.path}") String apiDocsPath) {
        this.baseUrl = apiDocsPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger-ui/")
                .setViewName("forward:/swagger-ui/index.html");
    }
}
