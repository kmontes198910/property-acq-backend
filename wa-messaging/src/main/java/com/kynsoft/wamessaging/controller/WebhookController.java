package com.kynsoft.wamessaging.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.wamessaging.application.command.webhook.ProcessWebhookCommand;
import com.kynsoft.wamessaging.application.command.webhook.ProcessWebhookMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * Controlador para recibir notificaciones de eventos/webhooks de WhatsApp
 */
@RestController
@RequestMapping("/api/whatsapp/webhook")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "WhatsApp Webhook", description = "Endpoints para recibir notificaciones y eventos de la API de WhatsApp")
public class WebhookController {

    private final IMediator mediator;
    
    @Value("${whatsapp.webhook.verify-token}")
    private String webhookVerifyToken;
    
    /**
     * Endpoint para recibir webhooks de WhatsApp Business API
     * Los webhooks incluyen actualizaciones de estado de mensajes enviados y mensajes entrantes
     */
    @PostMapping
    @Operation(summary = "Recibir webhook", description = "Recibe notificaciones y eventos desde la API de WhatsApp")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evento procesado correctamente")
    })
    public ResponseEntity<Map<String, String>> receiveWebhook(
            @Parameter(description = "Payload JSON con la notificación de WhatsApp", required = true)
            @RequestBody String payload) {
        log.info("Webhook recibido de WhatsApp: {}", payload);
        
        try {
            // Crear y enviar el comando para procesar el webhook
            ProcessWebhookCommand command = new ProcessWebhookCommand(payload);
            ProcessWebhookMessage response = mediator.send(command);
            
            // Devolver el resultado del procesamiento
            return ResponseEntity.ok(response.getResult());
            
        } catch (Exception e) {
            log.error("Error al procesar webhook de WhatsApp", e);
            return ResponseEntity.ok(Map.of(
                "status", "error", 
                "message", e.getMessage()
            ));
        }
    }
    
    /**
     * Endpoint de verificación de webhook
     * WhatsApp Business API requiere que respondamos a esta verificación
     * para confirmar que somos el propietario del webhook.
     */
    @GetMapping
    @Operation(summary = "Verificar webhook", description = "Verifica la propiedad del webhook para WhatsApp")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Verificación exitosa"),
        @ApiResponse(responseCode = "400", description = "Verificación fallida")
    })
    public ResponseEntity<String> verifyWebhook(
            @Parameter(description = "Modo de verificación, debe ser 'subscribe'", required = true)
            @RequestParam("hub.mode") String mode,
            
            @Parameter(description = "Token de verificación", required = true)
            @RequestParam("hub.verify_token") String token,
            
            @Parameter(description = "Challenge string que debe ser devuelto en caso de éxito", required = true)
            @RequestParam("hub.challenge") String challenge) {
        
        log.info("Recibida solicitud de verificación de webhook. Mode: {}, token: {}", mode, token);
        
        if ("subscribe".equals(mode) && webhookVerifyToken.equals(token)) {
            log.info("Verificación de webhook exitosa");
            return ResponseEntity.ok(challenge);
        }
        
        log.warn("Verificación de webhook fallida. Mode: {}, token: {}", mode, token);
        return ResponseEntity.badRequest().body("Verificación fallida");
    }
}
