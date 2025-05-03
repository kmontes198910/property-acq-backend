package com.kynsoft.propertyacqcenter.infrastructure.services.http.property;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.mock.RentCastServiceMockImpl;
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
    @Value("${rentcast.api.key:http://localhost:8097/api/rentcast/mock}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final RentCastServiceMockImpl resCastServiceMockImpl;

    public RentCastPropertyServiceImpl(RestTemplate restTemplate, RentCastServiceMockImpl resCastServiceMockImpl) {
        this.restTemplate = restTemplate;
        this.resCastServiceMockImpl = resCastServiceMockImpl;
    }

    //TODO: La response de este metodo, lo vamos a trasformar en la capa de application.
    public List<PropertyDto> getPropertyDetails(String address) {
        try {
            String url = apiKey + "/property/fake";

            // Crear cabeceras para la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Crear la entidad de la solicitud con el cuerpo (request) y las cabeceras
            HttpEntity<UUID> entity = new HttpEntity<>(UUID.randomUUID(), headers);

            // Usar ParameterizedTypeReference para especificar el tipo genérico
            ParameterizedTypeReference<List<PropertyDto>> responseType
                    = new ParameterizedTypeReference<List<PropertyDto>>() {
            };

            // Enviar la solicitud POST al endpoint del controlador
            ResponseEntity<List<PropertyDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    responseType);

            if (!HttpStatus.OK.equals(response.getStatusCode())) {
                throw new BusinessNotFoundException(new GlobalBusinessException(
                        DomainErrorMessage.BUSINESS_NOT_FOUND,
                        new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())
                ));
            }
            return response.getBody();
        } catch (RestClientException e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.BUSINESS_NOT_FOUND,
                    new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())
            ));
        }
    }
}
