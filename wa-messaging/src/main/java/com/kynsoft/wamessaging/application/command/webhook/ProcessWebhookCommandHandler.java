package com.kynsoft.wamessaging.application.command.webhook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.wamessaging.application.service.MessageCoordinatorService;
import com.kynsoft.wamessaging.domain.service.IncomingMessageService;
import com.kynsoft.wamessaging.infrastructure.entity.IncomingMessage;
import com.kynsoft.wamessaging.infrastructure.entity.MessageStatus;
import com.kynsoft.wamessaging.infrastructure.entity.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Manejador para procesar webhooks entrantes de WhatsApp
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessWebhookCommandHandler implements ICommandHandler<ProcessWebhookCommand> {

    private final MessageCoordinatorService messageCoordinatorService;
    private final IncomingMessageService incomingMessageService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void handle(ProcessWebhookCommand command) {
        log.info("Procesando webhook de WhatsApp: {}", command.getPayload());
        
        ProcessWebhookMessage response = new ProcessWebhookMessage(command.getPayload());
        
        try {
            JsonNode jsonNode = objectMapper.readTree(command.getPayload());
            
            // Validar y extraer información de la notificación
            if (jsonNode.has("entry") && jsonNode.get("entry").isArray() && jsonNode.get("entry").size() > 0) {
                JsonNode entry = jsonNode.get("entry").get(0);
                
                // Procesar mensajes entrantes
                if (entry.has("changes") && entry.get("changes").isArray() && entry.get("changes").size() > 0) {
                    JsonNode change = entry.get("changes").get(0);
                    JsonNode value = change.get("value");
                    
                    // Procesar actualizaciones de estado
                    if (value.has("statuses") && value.get("statuses").isArray() && value.get("statuses").size() > 0) {
                        processStatusUpdates(value, response);
                    }
                    
                    // Procesar mensajes entrantes
                    if (value.has("messages") && value.get("messages").isArray() && value.get("messages").size() > 0) {
                        processIncomingMessages(value, response);
                    }
                }
            }
            
        } catch (Exception e) {
            log.error("Error al procesar webhook de WhatsApp", e);
            response.getResult().put("status", "error");
            response.getResult().put("message", e.getMessage());
        }

    }
    
    /**
     * Procesa las actualizaciones de estado de mensajes
     */
    private void processStatusUpdates(JsonNode value, ProcessWebhookMessage response) {
        JsonNode statusesNode = value.get("statuses");
        for (JsonNode statusNode : statusesNode) {
            String messageId = statusNode.path("id").asText();
            String statusValue = statusNode.path("status").asText();
            String errors = statusNode.has("errors") ? statusNode.get("errors").toString() : null;
            
            log.info("Actualización de estado de mensaje ID: {}, Estado: {}", messageId, statusValue);
            
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
            messageCoordinatorService.updateMessageStatus(messageId, newStatus, errors);
        }
        
        response.addProcessedInfo("processed", "status_updates");
    }
    
    /**
     * Procesa los mensajes entrantes
     */
    private void processIncomingMessages(JsonNode value, ProcessWebhookMessage response) {
        JsonNode messagesNode = value.get("messages");
        JsonNode contactsNode = value.path("contacts");
        
        for (JsonNode messageNode : messagesNode) {
            try {
                String messageId = messageNode.path("id").asText();
                String senderPhone = messageNode.path("from").asText();
                long timestamp = messageNode.path("timestamp").asLong();
                String type = messageNode.path("type").asText();
                
                // Convertir timestamp a LocalDateTime
                LocalDateTime receivedAt = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(timestamp), 
                    ZoneId.systemDefault()
                );
                
                // Buscar nombre del contacto (si está disponible)
                String senderName = null;
                if (contactsNode != null && !contactsNode.isMissingNode() && contactsNode.isArray()) {
                    for (JsonNode contact : contactsNode) {
                        if (contact.path("wa_id").asText().equals(senderPhone)) {
                            JsonNode profile = contact.path("profile");
                            if (!profile.isMissingNode()) {
                                senderName = profile.path("name").asText(null);
                            }
                            break;
                        }
                    }
                }
                
                log.info("Mensaje entrante recibido - ID: {}, De: {}, Tipo: {}", messageId, senderPhone, type);
                
                // Crear objeto de mensaje entrante
                IncomingMessage message = IncomingMessage.builder()
                    .messageId(messageId)
                    .senderPhone(senderPhone)
                    .senderName(senderName)
                    .receivedAt(receivedAt)
                    .processed(false)
                    .build();
                
                // Procesar por tipo de mensaje
                switch (type.toLowerCase()) {
                    case "text":
                        processTextMessage(message, messageNode);
                        break;
                    case "image":
                        processMediaMessage(message, messageNode, MessageType.IMAGE);
                        break;
                    case "audio":
                        processMediaMessage(message, messageNode, MessageType.AUDIO);
                        break;
                    case "video":
                        processMediaMessage(message, messageNode, MessageType.VIDEO);
                        break;
                    case "document":
                        processMediaMessage(message, messageNode, MessageType.DOCUMENT);
                        break;
                    case "location":
                        processLocationMessage(message, messageNode);
                        break;
                    default:
                        message.setMessageType(MessageType.TEXT);
                        message.setContent("Mensaje de tipo no soportado: " + type);
                }
                
                // Procesar contexto (si es una respuesta a otro mensaje)
                JsonNode contextNode = messageNode.path("context");
                if (!contextNode.isMissingNode()) {
                    String contextMessageId = contextNode.path("id").asText(null);
                    message.setContextMessageId(contextMessageId);
                }
                
                // Guardar el mensaje en la base de datos
                incomingMessageService.saveIncomingMessage(message);
                
            } catch (Exception e) {
                log.error("Error al procesar mensaje entrante", e);
            }
        }
        
        response.addProcessedInfo("processed", "incoming_messages");
    }
    
    /**
     * Procesa un mensaje de texto
     */
    private void processTextMessage(IncomingMessage message, JsonNode messageNode) {
        String text = messageNode.path("text").path("body").asText("");
        message.setMessageType(MessageType.TEXT);
        message.setContent(text);
    }

    /**
     * Procesa un mensaje con contenido multimedia
     */
    private void processMediaMessage(IncomingMessage message, JsonNode messageNode, MessageType type) {
        JsonNode mediaNode = messageNode.path(type.toString().toLowerCase());
        message.setMessageType(type);
        
        // Guardar la URL del archivo si está disponible
        String mediaUrl = mediaNode.path("url").asText(null);
        if (mediaUrl != null) {
            message.setMediaUrl(mediaUrl);
        }
        
        // ID del multimedia en WhatsApp (para poder descargarlo después)
        String mediaId = mediaNode.path("id").asText(null);
        message.setMediaId(mediaId);
        
        // Guardar información adicional según el tipo
        String caption = mediaNode.path("caption").asText(null);
        String filename = mediaNode.path("filename").asText(null);
        String mimeType = mediaNode.path("mime_type").asText(null);
        
        message.setContent(caption != null ? caption : filename != null ? filename : "");
        message.setMimeType(mimeType);
    }

    /**
     * Procesa un mensaje de ubicación
     */
    private void processLocationMessage(IncomingMessage message, JsonNode messageNode) {
        JsonNode locationNode = messageNode.path("location");
        
        double latitude = locationNode.path("latitude").asDouble();
        double longitude = locationNode.path("longitude").asDouble();
        String name = locationNode.path("name").asText("");
        String address = locationNode.path("address").asText("");
        
        message.setMessageType(MessageType.LOCATION);
        message.setLatitude(latitude);
        message.setLongitude(longitude);
        message.setContent(String.format("%s\n%s\nLat: %f, Long: %f", 
            name, address, latitude, longitude));
    }
}
