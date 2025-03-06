package com.kynsoft.notification.infrastructure.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class QBotService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String LOGIN_URL = "https://api-test.theastrobyte.com/qbot/v1/login";
    private static final String SEND_MESSAGE_URL = "https://api-test.theastrobyte.com/qbot/v1/messages/sendMessage";

    public QBotService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    public String authenticate(String email, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Basic UUItU1RBR0VVc2VyOlFCLVNUQUdFUGFzc3dvcmQ=");
        headers.set("Content-Type", "application/json");
        // Establecer los encabezados de autenticación básica

        String url = LOGIN_URL;
        Map<String, String> request = Map.of(
                "email", "qbot.developer@gmail.com",
                "password", "DeveloPer.2025@"
        );
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Procesar la respuesta para extraer el token_auth
        if (response.getStatusCode().is2xxSuccessful()) {

            try {
                // Crear ObjectMapper para trabajar con JsonNode
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.getBody()); // Convertir el JSON en un árbol de nodos

                // Acceder directamente a 'data' y 'accessToken'
                JsonNode accessTokenNode = rootNode.path("data").path("accessToken");

                // Extraer el valor del accessToken como texto
                String accessToken = accessTokenNode.asText();

                return accessToken;  // Retorna el accessToken
            } catch (Exception e) {
                e.printStackTrace();
                return "Error al obtener el accessToken";
            }

        } else {
            return "Error al obtener el accessToken";
        }
    }

    public String sendMessage(String accessToken, String storeId, Map<String, String> requestData) {
      Map<String, String> messageRequest = new HashMap<>();
        messageRequest.put("storeId", storeId);
//        messageRequest.put("type", "text");
//        messageRequest.put("preview_url", "true");
//        messageRequest.put("phone", "593983825630");
//        messageRequest.put("message"," Hola, este es un mensaje de texto con un enlace. https://www.google.com");

        if (requestData != null) {
            messageRequest.putAll(requestData);
        }

        System.out.println("🔍 Final Message Request: " + messageRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(messageRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(SEND_MESSAGE_URL, HttpMethod.POST, request, String.class);

        return response.getBody();
    }
}