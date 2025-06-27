package com.kynsoft.propertyacqcenter.infrastructure.services.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsoft.propertyacqcenter.domain.dto.http.CreateUserResponse;
import com.kynsoft.propertyacqcenter.domain.dto.http.CreateUserSystemRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Configuration
@Slf4j
public class UserSystemService {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    // URL del servicio remoto
    @Value("${user.system.api.url:http://localhost:9090}")
    //@Value("${user.system.api.url:http://localhost:9909/api/users}")
    private String userSystemApiUrl;

    public UserSystemService(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public String createUserSystem(CreateUserSystemRequest value) throws IOException, URISyntaxException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(value);
        log.error("se llama al servicio de identity"+value.getUserName());
        String url = this.userSystemApiUrl + "/api/users";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        log.error("URL del servicio de identidad: " + url);
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al crear usuario en el sistema. Código de estado: " + response.statusCode() + ", Respuesta: " + response.body());
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al crear usuario en el sistema. Código de estado: " + response.statusCode() + ", Respuesta: " + response.body());
        }

        CreateUserResponse createUserResponse = objectMapper.readValue(response.body(), CreateUserResponse.class);

        return createUserResponse.getId();
    }
}