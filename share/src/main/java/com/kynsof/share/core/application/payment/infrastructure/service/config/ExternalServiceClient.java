package com.kynsof.share.core.application.payment.infrastructure.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ExternalServiceClient {

    private final PaymentServiceConfig paymentServiceConfig;
    private final ObjectMapper objectMapper; // Para convertir JSON a Map

    public ExternalServiceClient(PaymentServiceConfig paymentServiceConfig, ObjectMapper objectMapper) {
        this.paymentServiceConfig = paymentServiceConfig;
        this.objectMapper = objectMapper;
    }

    public PaymentServiceStatusResponse callExternalService(String requestId, String clientRegistrationId) throws IOException {
        // Construcción del endpoint
        String baseUrl = String.format("%s:%d",
                paymentServiceConfig.getPaymentServiceBaseUrl(),
                paymentServiceConfig.getPaymentServicePort());
        String endpoint = String.format(
                "%s/placetopay/%s/information/%s",
                baseUrl,
                paymentServiceConfig.getPaymentServiceClientId(),
                requestId
        );

        // Crear cliente HTTP
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost execute = new HttpPost(endpoint);
            try (CloseableHttpResponse response = httpClient.execute(execute)) {
                int statusCode = response.getCode();
                if (statusCode != 200) {
                    throw new IOException("Error en la llamada HTTP. Código de estado: " + statusCode);
                }

                String responseBody = EntityUtils.toString(response.getEntity());
                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
                return mapToPaymentServiceStatusResponse(responseMap);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private PaymentServiceStatusResponse mapToPaymentServiceStatusResponse(Map<String, Object> responseBody) {
        Map<String, Object> value = (Map<String, Object>) responseBody.get("value");
        Map<String, Object> status = (Map<String, Object>) value.get("status");

        PaymentServiceStatusResponse responseDto = new PaymentServiceStatusResponse();
        responseDto.setReference((String) value.get("reference"));
        responseDto.setAuthorization((String) value.get("authorization"));
        responseDto.setStatus((String) status.get("status"));
        responseDto.setReason((String) status.get("reason"));
        responseDto.setMessage((String) status.get("message"));
       // responseDto.setDate(status.get("date"));

        return responseDto;
    }
}