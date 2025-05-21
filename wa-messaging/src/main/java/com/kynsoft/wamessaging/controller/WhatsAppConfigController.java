package com.kynsoft.wamessaging.controller;

import com.kynsoft.wamessaging.infrastructure.config.WhatsAppConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para gestionar la configuración de WhatsApp
 */
@RestController
@RequestMapping("/api/whatsapp/config")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Configuración WhatsApp", description = "Endpoints para gestionar la configuración de WhatsApp")
public class WhatsAppConfigController {

    private final WhatsAppConfig whatsAppConfig;
    
    @Value("${server.port:8080}")
    private String serverPort;
    
    /**
     * Obtiene la información de configuración del webhook
     */
    @GetMapping("/webhook")
    @Operation(summary = "Información de webhook", description = "Obtiene información sobre la configuración del webhook")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Información de webhook obtenida correctamente",
            content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<Map<String, Object>> getWebhookInfo() {
        Map<String, Object> response = new HashMap<>();
        
        // URL base de la aplicación (esto debería configurarse correctamente en producción)
        String baseUrl = getBaseUrl();
        
        // URL completa del webhook
        String webhookUrl = baseUrl + "/api/whatsapp/webhook";
        
        // Información de configuración
        response.put("webhookUrl", webhookUrl);
        response.put("verificationToken", whatsAppConfig.getWebhook().getVerifyToken());
        response.put("configuracionWhatsApp", Map.of(
            "url", webhookUrl,
            "verificacionRequerida", true,
            "camposRecomendados", new String[]{
                "messages", "message_status_updates"
            }
        ));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Obtiene la URL base de la aplicación
     * En un entorno de producción esto debería configurarse adecuadamente
     */
    private String getBaseUrl() {
        // En producción, esta URL debería configurarse según el entorno
        return "https://wa-messaging-service.example.com";
    }
}
