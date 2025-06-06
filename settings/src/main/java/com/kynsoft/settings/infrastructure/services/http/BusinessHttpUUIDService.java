package com.kynsoft.settings.infrastructure.services.http;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.settings.infrastructure.services.http.dto.BusinessHttp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class BusinessHttpUUIDService {

    private final RestTemplate restTemplate;

    @Value("${business.service:http://localhost:9090}")
//    @Value("${patient.service:http://invoicing.finamer.svc.cluster.local:9909}")
    private String serviceUrl;

    public BusinessHttpUUIDService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BusinessHttp sendGetHttpRequest(UUID id) {
        try {
            String url = serviceUrl + "/api/business/http/replicate/" + id;

            // Crear cabeceras para la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Crear la entidad de la solicitud con el cuerpo (request) y las cabeceras
            HttpEntity<UUID> entity = new HttpEntity<>(id, headers);

            // Enviar la solicitud POST al endpoint del controlador
            ResponseEntity<BusinessHttp> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, BusinessHttp.class);

            if (!HttpStatus.OK.equals(response.getStatusCode())) {
                throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())));
            }
            return response.getBody();
        } catch (RestClientException e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())));
        }
    }
}
