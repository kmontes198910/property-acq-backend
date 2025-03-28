package com.kynsof.share.core.application.payment.infrastructure.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceClient {

    private final PaymentServiceConfig paymentServiceConfig;
    private final ObjectMapper objectMapper;

    public PaymentServiceClient(PaymentServiceConfig paymentServiceConfig, ObjectMapper objectMapper) {
        this.paymentServiceConfig = paymentServiceConfig;
        this.objectMapper = objectMapper;
    }

    public PaymentServiceStatusResponse validateStatusPayment(String requestId, UUID businessId) throws IOException {
        String endpoint = buildEndpoint("placetopay/%s/information/%s", businessId, requestId);
        return sendPostRequest(endpoint);
    }

    public PaymentServiceStatusResponse reverseTransaction(UUID businessId, String reference) throws IOException {
        String endpoint = buildEndpoint("placetopay/%s/transactions/%s", businessId, reference);
        return sendPostRequest(endpoint);
    }

    private String buildEndpoint(String pathTemplate, UUID id, String param) {
        String baseUrl = paymentServiceConfig.getPaymentServiceBaseUrl();
        if (!baseUrl.startsWith("http://") && !baseUrl.startsWith("https://")) {
            baseUrl = "http://" + baseUrl;
        }
        return String.format("%s:%d/" + pathTemplate, baseUrl, paymentServiceConfig.getPaymentServicePort(), id, param);
    }

    private PaymentServiceStatusResponse sendPostRequest(String endpoint) throws IOException {
        System.out.println("Generated URL: " + endpoint);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(endpoint);
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                int statusCode = response.getCode();
                if (statusCode != 200) {
                    throw new IOException("Error en la llamada HTTP. Código de estado: " + statusCode);
                }

                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("HTTP Response Body >>> " + responseBody);

                Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
                boolean isSuccess = Boolean.parseBoolean(String.valueOf(responseMap.get("isSuccess")));

                if (!isSuccess) {
                    return null;
                }

                return mapToPaymentServiceStatusResponse(responseMap);
            } catch (ParseException e) {
                throw new RuntimeException("Error al parsear la respuesta HTTP", e);
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

        return responseDto;
    }
}