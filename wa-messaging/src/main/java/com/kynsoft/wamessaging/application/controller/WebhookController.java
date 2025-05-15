package com.kynsoft.wamessaging.application.controller;

import com.kynsoft.wamessaging.application.service.MessageCoordinatorService;
import com.kynsoft.wamessaging.domain.entity.MessageStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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

    private final MessageCoordinatorService messageCoordinatorService;
    
    @Value("${whatsapp.webhook.verify-token}")
    private String webhookVerifyToken;
    
    /**
     * Endpoint para recibir webhooks de WhatsApp Business API
     * Los webhooks incluyen actualizaciones de estado de mensajes enviados
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
            JSONObject json = new JSONObject(payload);
            
            // Validar y extraer información de la notificación
            if (json.has("entry") && json.getJSONArray("entry").length() > 0) {
                JSONObject entry = json.getJSONArray("entry").getJSONObject(0);
                
                if (entry.has("changes") && entry.getJSONArray("changes").length() > 0) {
                    JSONObject change = entry.getJSONArray("changes").getJSONObject(0);
                    JSONObject value = change.getJSONObject("value");
                    
                    // Procesar actualizaciones de estado
                    if (value.has("statuses") && value.getJSONArray("statuses").length() > 0) {
                        JSONObject status = value.getJSONArray("statuses").getJSONObject(0);
                        String messageId = status.optString("id");
                        String statusValue = status.optString("status");
                        String error = status.has("errors") ? status.getJSONArray("errors").toString() : null;
                        
                        // Mapear el estado de WhatsApp a nuestro enum MessageStatus
                        MessageStatus newStatus;
                        switch (statusValue) {
                            case "sent":
                                newStatus = MessageStatus.SENT;
                                break;
                            case "delivered":
                                newStatus = MessageStatus.DELIVERED;
                                break;
                            case "read":
                                newStatus = MessageStatus.READ;
                                break;
                            case "failed":
                                newStatus = MessageStatus.FAILED;
                                break;
                            default:
                                newStatus = MessageStatus.UNKNOWN;
                        }
                        
                        // Actualizar el estado del mensaje en nuestra BD
                        messageCoordinatorService.updateMessageStatus(messageId, newStatus, error);
                    }
                }
            }
            
            // Siempre responder con éxito para que WhatsApp sepa que recibimos la notificación
            return ResponseEntity.ok(Map.of("status", "success"));
            
        } catch (Exception e) {
            log.error("Error al procesar webhook de WhatsApp", e);
            return ResponseEntity.ok(Map.of("status", "error", "message", e.getMessage()));
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
        
        // Aquí debes verificar que el token coincida con el configurado en tu aplicación
        // para seguridad en la configuración de webhook
        
        if ("subscribe".equals(mode) && webhookVerifyToken.equals(token)) {
            log.info("Verificación de webhook exitosa");
            return ResponseEntity.ok(challenge);
        } else {
            log.warn("Fallo en la verificación de webhook");
            return ResponseEntity.badRequest().build();
        }
    }
}
