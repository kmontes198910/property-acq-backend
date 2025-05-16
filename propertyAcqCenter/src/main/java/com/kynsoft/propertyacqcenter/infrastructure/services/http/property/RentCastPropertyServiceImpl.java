package com.kynsoft.propertyacqcenter.infrastructure.services.http.property;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.mock.RentCastServiceMockImpl;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClientException;

@Service
public class RentCastPropertyServiceImpl {

    //TODO: esta api debe de ser cambiada por la real a consumir.
    @Value("${rentcast.api.key:956392a6c15d4dca8e25623f87c8121b}")
    private String apiKey;

    //private final String BASE_URL = "http://localhost:8097/api/rentcast/mock";
//    private final String BASE_URL = "http://property-acq-center-service:9901/api/rentcast/mock";
    private final String BASE_URL = "https://api.rentcast.io/v1";

    private final RestTemplate restTemplate;
    private final RentCastServiceMockImpl resCastServiceMockImpl;

    public RentCastPropertyServiceImpl(RestTemplate restTemplate, RentCastServiceMockImpl resCastServiceMockImpl) {
        this.restTemplate = restTemplate;
        this.resCastServiceMockImpl = resCastServiceMockImpl;
    }

    private HttpEntity<String> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        return new HttpEntity<>(headers);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    //TODO: La response de este metodo, lo vamos a trasformar en la capa de application.
    public List<PropertyResponse> getPropertyDetails(String address) {
        try {
            String cleanedAddress = address.trim(); // Elimina espacios al inicio/final
            String encodedAddress = URLEncoder.encode(cleanedAddress, StandardCharsets.UTF_8);

            //verdadero
            String url = BASE_URL + "/properties?address=" + cleanedAddress;
            //String url = BASE_URL + "/property/fake";

            System.err.println("Url: " + url);
            // Crear cabeceras para la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Crear la entidad de la solicitud con el cuerpo (request) y las cabeceras
            HttpEntity<UUID> entity = new HttpEntity<>(UUID.randomUUID(), headers);

            // Usar ParameterizedTypeReference para especificar el tipo genérico
            ParameterizedTypeReference<List<PropertyResponse>> responseType
                    = new ParameterizedTypeReference<List<PropertyResponse>>() {
            };

            // Enviar la solicitud POST al endpoint del controlador
            ResponseEntity<List<PropertyResponse>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    createHttpEntity(),
                    responseType
            );

            if (!HttpStatus.OK.equals(response.getStatusCode())) {
                throw new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.BUSINESS_NOT_FOUND,
                        new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())
                ));
            }
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException(e);
//            throw new BusinessNotFoundException(new GlobalBusinessException(
//                    DomainErrorMessage.BUSINESS_NOT_FOUND,
//                    new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())
//            ));
        }
    }
}
