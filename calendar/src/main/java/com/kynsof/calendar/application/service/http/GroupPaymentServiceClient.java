package com.kynsof.calendar.application.service.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class GroupPaymentServiceClient {

    private final ObjectMapper objectMapper;
    private final String apiUrl;

    public GroupPaymentServiceClient(ObjectMapper objectMapper,
                                     @Value("${payment.service.base-url}") String baseUrl) {
        this.objectMapper = objectMapper;
        this.apiUrl = baseUrl + "/api/group-payments/completed";
    }

    public String createCompleted(CreateGroupPaymentUnifRequest request) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(apiUrl);
            httpPost.setHeader("Content-Type", "application/json");

            // Convertir el request a JSON y agregarlo en el cuerpo de la petición
            String jsonRequest = objectMapper.writeValueAsString(request);
            httpPost.setEntity(new StringEntity(jsonRequest, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();
                if (statusCode != 200) {
                    throw new IOException("Error en la llamada HTTP. Código de estado: " + statusCode);
                }

                // Leer respuesta y extraer solo el campo "id"
                String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                return jsonNode.get("id").asText();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

}