package com.kynsoft.wamessaging.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Configuración para la integración con WhatsApp
 */
@Configuration
@ConfigurationProperties(prefix = "whatsapp")
@Validated
@Getter
@Setter
public class WhatsAppConfig {

    /**
     * Configuración del webhook
     */
    private Webhook webhook = new Webhook();
    
    /**
     * Configuración de la API
     */
    private Api api = new Api();
    
    /**
     * Configuración del webhook de WhatsApp
     */
    @Getter
    @Setter
    public static class Webhook {
        
        /**
         * Token de verificación usado para validar las solicitudes de webhook
         */
        @NotBlank(message = "El token de verificación del webhook no puede estar vacío")
        private String verifyToken;
        
        /**
         * Endpoint público para la recepción de webhooks
         * Esta es la URL que debe configurarse en la consola de WhatsApp Business
         */
        private String endpointUrl = "/webhook";
    }
    
    /**
     * Configuración para la API de WhatsApp Business
     */
    @Getter
    @Setter
    public static class Api {
        
        /**
         * URL base de la API de WhatsApp
         */
        @NotBlank(message = "La URL base de la API es obligatoria")
        private String baseUrl = "https://graph.facebook.com/v17.0";
        
        /**
         * ID de cuenta de WhatsApp Business
         */
        @NotBlank(message = "El ID de la cuenta de WhatsApp Business es obligatorio")
        private String accountId;
        
        /**
         * Token de acceso para la API de WhatsApp
         */
        @NotBlank(message = "El token de acceso es obligatorio")
        private String accessToken;
        
        /**
         * ID del número de teléfono en WhatsApp Business
         */
        @NotBlank(message = "El ID del número de teléfono es obligatorio")
        private String phoneNumberId;
    }
}
