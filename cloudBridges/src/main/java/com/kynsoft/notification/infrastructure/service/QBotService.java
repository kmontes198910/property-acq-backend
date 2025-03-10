package com.kynsoft.notification.infrastructure.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class QBotService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${qbot.api.login-url}")
    private String loginUrl;

    @Value("${qbot.api.send-message-url}")
    private String sendMessageUrl;

    @Value("${qbot.api.email}")
    private String email;

    @Value("${qbot.api.password}")
    private String password;

    @Value("${qbot.api.authorization}")
    private String authorization;

    @Value("${qbot.api.store}")
    private String store;

    public QBotService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String authenticate() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("Content-Type", "application/json");

        Map<String, String> request = Map.of(
                "email", email,
                "password", password
        );
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(loginUrl, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                JsonNode rootNode = objectMapper.readTree(response.getBody());
                return rootNode.path("data").path("accessToken").asText();
            } catch (Exception e) {
                e.printStackTrace();
                return "Error al obtener el accessToken";
            }
        } else {
            return "Error al obtener el accessToken";
        }
    }

    public String sendMessage(String accessToken, Map<String, String> requestData) {
        Map<String, String> messageRequest = new HashMap<>(requestData);
        messageRequest.put("storeId", store);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(messageRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(sendMessageUrl, HttpMethod.POST, request, String.class);

        return response.getBody();
    }
}
