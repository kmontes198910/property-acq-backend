package com.kynsoft.wamessaging.infrastructure.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsoft.wamessaging.application.dto.WhatsAppApiResponse;
import com.kynsoft.wamessaging.domain.entity.MessageType;
import com.kynsoft.wamessaging.domain.service.WhatsAppApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación del cliente para la API de WhatsApp Business Cloud
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WhatsAppApiClientImpl implements WhatsAppApiClient {

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
//    private final MessageProcessorService messageProcessorService;

    @Value("${whatsapp.api.url}")
    private String apiUrl;
    
    @Value("${whatsapp.api.phone-number-id}")
    private String phoneNumberId;
    
    @Value("${whatsapp.api.token}")
    private String apiToken;
    
    @Value("${whatsapp.api.version:v17.0}")
    private String apiVersion;

    /**
     * Envía un mensaje de texto simple
     */
    @Override
    public WhatsAppApiResponse sendTextMessage(String recipientPhone, String message) {
        log.info("Enviando mensaje de texto a {}: {}", recipientPhone, message);
        
        Map<String, Object> payload = new LinkedHashMap<>();  // Usar LinkedHashMap para mantener el orden
        payload.put("messaging_product", "whatsapp");
        payload.put("type", "text");  // Mover type antes de text
        payload.put("recipient_type", "individual");
        payload.put("to", recipientPhone);
        payload.put("text", Map.of("body", message));  // Simplificar la creación del objeto text
        
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            log.debug("Payload generado para mensaje de texto: {}", jsonPayload);
            return sendRequest(jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("Error al crear payload para mensaje de texto", e);
            return buildErrorResponse("Error al crear mensaje: " + e.getMessage());
        }
    }
    
    /**
     * Envía un mensaje basado en plantilla
     */
    @Override
    public WhatsAppApiResponse sendTemplateMessage(String recipientPhone, String templateName, Map<String,Object> templateData) {

        log.info("Enviando mensaje de plantilla a {}: plantilla {}", recipientPhone, templateName);
        
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", recipientPhone);
        payload.put("type", "template");
        Map<String, Object> template = new LinkedHashMap<>();
        template.put("name", templateName);
        
        // Procesar lenguaje
        Map<String, String> language = new LinkedHashMap<>();
        language.put("code", "es");
        template.put("language", language);
        
        // Procesar componentes si existen
        List<Map<String, Object>> components = new ArrayList<>();


        List<Map<String, Object>> templateComponents = new ArrayList<>();
        Map<String, Object> textParam = new HashMap<>();
        textParam.put("type", "text");
        textParam.put("text", "Juan");

        Map<String, Object> headerComponent = new HashMap<>();
        headerComponent.put("type", "header");
        headerComponent.put("parameters", List.of(textParam));


        Map<String, Object> param1 = new HashMap<>();
        param1.put("type", "text");
        param1.put("text", "$10,22");

        Map<String, Object> param2 = new HashMap<>();
        param2.put("type", "text");
        param2.put("text", "1234567890P");

        Map<String, Object> bodyComponent = new HashMap<>();
        bodyComponent.put("type", "body");
        bodyComponent.put("parameters", List.of(param1, param2));


        Map<String, Object> buttonParam = new HashMap<>();
        buttonParam.put("type", "text");
        buttonParam.put("text", "https://tes.com");

        Map<String, Object> buttonComponent = new HashMap<>();
        buttonComponent.put("type", "button");
        buttonComponent.put("sub_type", "url");
        buttonComponent.put("index", "0");
        buttonComponent.put("parameters", List.of(buttonParam));

        templateComponents.add(headerComponent);
        templateComponents.add(bodyComponent);
        templateComponents.add(buttonComponent);

       template.put("components", templateComponents);

        payload.put("template", template);

        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            log.debug("Payload generado: {}", jsonPayload);
            return sendRequest(jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("Error al crear payload para mensaje de plantilla", e);
            return buildErrorResponse("Error al crear mensaje: " + e.getMessage());
        }
    }

    


    
    /**
     * Envía un mensaje con contenido multimedia
     */
    @Override
    public WhatsAppApiResponse sendMediaMessage(String recipientPhone, String caption,  MessageType mediaType) {
     //   log.info("Enviando mensaje multimedia ({}) a {}: {}", mediaType, recipientPhone, mediaUrl);
        
        String mediaTypeStr = mediaType.name().toLowerCase();
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("recipient_type", "individual");
        payload.put("to", recipientPhone);
        payload.put("type", mediaTypeStr);
        
        Map<String, Object> mediaContent = new HashMap<>();
       // mediaContent.put("link", mediaUrl);
        if (caption != null && !caption.isEmpty()) {
            mediaContent.put("caption", caption);
        }
        
        payload.put(mediaTypeStr, mediaContent);
        
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            return sendRequest(jsonPayload);
        } catch (JsonProcessingException e) {
            log.error("Error al crear payload para mensaje multimedia", e);
            return buildErrorResponse("Error al crear mensaje: " + e.getMessage());
        }
    }

    @Override
    public WhatsAppApiResponse checkMessageStatus(UUID messageId) {
        log.info("Verificando estado del mensaje: {}", messageId);
        
        String url = String.format("%s/%s/%s/messages/%s", apiUrl, apiVersion, phoneNumberId, messageId.toString());
        
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + apiToken)
                .get()
                .build();
        
        try {
            Response response = httpClient.newCall(request).execute();
            return processResponse(response);
        } catch (IOException e) {
            log.error("Error al verificar estado del mensaje", e);
            return buildErrorResponse("Error de conexión: " + e.getMessage());
        }
    }


    
    /**
     * Método común para enviar las solicitudes a la API
     */
    private WhatsAppApiResponse sendRequest(String jsonPayload) {
        String url = String.format("%s/%s/%s/messages", apiUrl, apiVersion, phoneNumberId);
        log.debug("Enviando solicitud a {}: {}", url, jsonPayload);
        
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(jsonPayload, mediaType);
        
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + apiToken)
                .header("Content-Type", "application/json")
                .post(body)
                .build();
        
        try {
            Response response = httpClient.newCall(request).execute();
            return processResponse(response);
        } catch (IOException e) {
            log.error("Error al enviar mensaje", e);
            return buildErrorResponse("Error de conexión: " + e.getMessage());
        }
    }
    
    /**
     * Procesa la respuesta de la API
     */
    private WhatsAppApiResponse processResponse(Response response) throws IOException {
        if (!response.isSuccessful()) {
            log.error("Error en la respuesta de la API: Código {}", response.code());
            return buildErrorResponse("Error HTTP: " + response.code() + " " + response.message());
        }
        
        String responseBody = response.body() != null ? response.body().string() : "";
        log.debug("Respuesta de la API: {}", responseBody);
        
        try {
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
            
            WhatsAppApiResponse.WhatsAppApiResponseBuilder responseBuilder = WhatsAppApiResponse.builder()
                    .successful(response.isSuccessful())
                    .messagingProduct((String) responseMap.get("messaging_product"));
            
            // Procesar ID del mensaje si existe
            if (responseMap.containsKey("messages")) {
                Object messagesObj = responseMap.get("messages");
                if (messagesObj instanceof Iterable) {
                    Iterable<?> messages = (Iterable<?>) messagesObj;
                    for (Object msg : messages) {
                        if (msg instanceof Map) {
                            Map<?, ?> messageMap = (Map<?, ?>) msg;
                            if (messageMap.containsKey("id")) {
                                responseBuilder.id(messageMap.get("id").toString());
                                responseBuilder.messages(objectMapper.writeValueAsString(messagesObj));
                                break;
                            }
                        }
                    }
                }
            }
            
            // Procesar información de contactos si existe
            if (responseMap.containsKey("contacts")) {
                responseBuilder.contacts(objectMapper.writeValueAsString(responseMap.get("contacts")));
            }
            
            // Procesar errores si existen
            if (responseMap.containsKey("error")) {
                Object errorObj = responseMap.get("error");
                if (errorObj instanceof Map) {
                    Map<?, ?> error = (Map<?, ?>) errorObj;
                    responseBuilder
                            .successful(false)
                            .errorCode(error.containsKey("code") ? error.get("code").toString() : null)
                            .errorMessage(error.containsKey("message") ? error.get("message").toString() : null);
                }
            }
            
            return responseBuilder.build();
            
        } catch (Exception e) {
            log.error("Error al procesar respuesta JSON", e);
            return buildErrorResponse("Error al procesar respuesta: " + e.getMessage());
        }
    }
    
    /**
     * Construye una respuesta de error
     */
    private WhatsAppApiResponse buildErrorResponse(String errorMessage) {
        log.error("Respuesta de error: {}", errorMessage);
        return WhatsAppApiResponse.builder()
                .successful(false)
                .errorMessage(errorMessage)
                .build();
    }

}