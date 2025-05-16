//package com.kynsoft.wamessaging.infrastructure.service;
//
//import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
//import com.kynsoft.wamessaging.application.dto.WhatsAppApiResponse;
//import com.kynsoft.wamessaging.domain.entity.MessageType;
//import com.kynsoft.wamessaging.domain.service.WhatsAppApiClient;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class MessageProcessorService {
//
//    private final WhatsAppApiClient whatsAppApiClient;
//
//    /**
//     * Procesa y envía un mensaje utilizando la plantilla especificada
//     */
//    public WhatsAppApiResponse processTemplateMessage(SendMessageRequest request) {
//        log.debug("Procesando mensaje de plantilla: {}", request);
//
//        // Validaciones básicas
//        if (!validarRequest(request)) {
//            return buildErrorResponse("Request inválido: faltan campos requeridos");
//        }
//
//        try {
//            // Si es un mensaje de texto simple
//            if ("text".equalsIgnoreCase(request.getMessageType())) {
//                return procesarMensajeTexto(request);
//            }
//            // Si es un mensaje de plantilla
//            else if ("template".equalsIgnoreCase(request.getMessageType())) {
//                return procesarMensajePlantilla(request);
//            }
//            // Si es un mensaje multimedia
//            else if (isMediaType(request.getMessageType())) {
//                return procesarMensajeMultimedia(request);
//            }
//            else {
//                return buildErrorResponse("Tipo de mensaje no soportado: " + request.getMessageType());
//            }
//        } catch (Exception e) {
//            log.error("Error procesando mensaje: {}", e.getMessage(), e);
//            return buildErrorResponse("Error procesando mensaje: " + e.getMessage());
//        }
//    }
//
//    private boolean validarRequest(SendMessageRequest request) {
//        return request != null
//               && StringUtils.hasText(request.getRecipientPhone())
//               && StringUtils.hasText(request.getMessageType());
//    }
//
//    private WhatsAppApiResponse procesarMensajeTexto(SendMessageRequest request) {
//        if (!StringUtils.hasText(request.getTemplateData().getBody().get(0))) {
//            return buildErrorResponse("El texto del mensaje es requerido");
//        }
//        return whatsAppApiClient.sendTextMessage(
//                request.getRecipientPhone(),
//                request.getTemplateData().getBody().get(0)
//        );
//    }
//
//    private WhatsAppApiResponse procesarMensajePlantilla(SendMessageRequest request) {
//        if (!StringUtils.hasText(request.getTemplateName())) {
//            return buildErrorResponse("El nombre de la plantilla es requerido");
//        }
//        if (request.getTemplateData() == null) {
//            return buildErrorResponse("Los datos de la plantilla son requeridos");
//        }
//
//        Map<String, Object> templateData = request.toTemplateDataMap();
//        return whatsAppApiClient.sendTemplateMessage(
//                request.getRecipientPhone(),
//                request.getTemplateName(),
//                templateData
//        );
//    }
//
//    private WhatsAppApiResponse procesarMensajeMultimedia(SendMessageRequest request) {
//        if (request.getTemplateData() == null || request.getTemplateData().getBody() == null
//            || request.getTemplateData().getBody().isEmpty()) {
//            return buildErrorResponse("La URL del contenido multimedia es requerida");
//        }
//
//        String mediaUrl = request.getTemplateData().getBody().get(0);
//        String caption = request.getTemplateData().getHeader(); // El header se usa como caption
//        MessageType mediaType = convertToMediaType(request.getMessageType());
//
//        return whatsAppApiClient.sendMediaMessage(
//                request.getRecipientPhone(),
//                caption,
//                mediaUrl,
//                mediaType
//        );
//    }
//
//    private boolean isMediaType(String messageType) {
//        try {
//            MessageType.valueOf(messageType.toUpperCase());
//            return true;
//        } catch (IllegalArgumentException e) {
//            return false;
//        }
//    }
//
//    private MessageType convertToMediaType(String messageType) {
//        try {
//            return MessageType.valueOf(messageType.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Tipo de mensaje multimedia no válido: " + messageType);
//        }
//    }
//
//    private WhatsAppApiResponse buildErrorResponse(String errorMessage) {
//        log.error(errorMessage);
//        return WhatsAppApiResponse.builder()
//                .successful(false)
//                .errorMessage(errorMessage)
//                .build();
//    }
//}
