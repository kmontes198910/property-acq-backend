package com.kynsoft.wamessaging.application.controller;

import com.kynsoft.wamessaging.application.service.MessageCoordinatorService;
import com.kynsoft.wamessaging.domain.entity.MessageStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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
public class WebhookController {

    private final MessageCoordinatorService messageCoordinatorService;
    
    /**
     * Endpoint para recibir webhooks de WhatsApp Business API
     * Los webhooks incluyen actualizaciones de estado de mensajes enviados
     */
    @PostMapping
    public ResponseEntity<Map<String, String>> receiveWebhook(@RequestBody String payload) {
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
    public ResponseEntity<String> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge) {
        
        log.info("Recibida solicitud de verificación de webhook. Mode: {}, token: {}", mode, token);
        
        // Aquí debes verificar que el token coincida con el configurado en tu aplicación
        // para seguridad en la configuración de webhook
        String expectedToken = "tu-token-secreto"; // Esto debería estar en una variable de entorno
        
        if ("subscribe".equals(mode) && expectedToken.equals(token)) {
            log.info("Verificación de webhook exitosa");
            return ResponseEntity.ok(challenge);
        } else {
            log.warn("Fallo en la verificación de webhook");
            return ResponseEntity.badRequest().build();
        }
    }
}
