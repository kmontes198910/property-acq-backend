package com.kynsoft.propertyacqcenter.infrastructure.services.http.market;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.propertyacqcenter.application.response.market.RealEstateResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClientException;

@Service
public class MarketServiceImpl {

    //TODO: esta api debe de ser cambiada por la real a consumir.
    @Value("${rentcast.api.key:956392a6c15d4dca8e25623f87c8121b}")
    private String apiKey;

//    private final String BASE_URL = "http://localhost:8097/api/rentcast/mock";
//    private final String BASE_URL = "http://property-acq-center-service:9901/api/rentcast/mock";
    private final String BASE_URL = "https://api.rentcast.io/v1";

    private final RestTemplate restTemplate;

    public MarketServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpEntity<String> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        return new HttpEntity<>(headers);
    }

    //TODO: La response de este metodo, lo vamos a trasformar en la capa de application.
    //@Cacheable(value = "propertyCache", key = "#address", unless = "#result == null")
    public RealEstateResponse getMarketDetails(String dataType,
                                               String zipCode,
                                               int historyRange) {
        try {
            String zipCodeUrl = !zipCode.equals("") ? "zipCode=" + zipCode : null;

            String historyRangeUrl = historyRange != 0 ? "historyRange=" + historyRange : null;
            //verdadero
            String url = BASE_URL + "/markets?dataType=" + dataType;

            if (zipCodeUrl != null) {
                url = url + "&" + zipCodeUrl;
            }
            if (historyRangeUrl != null) {
                url = url + "&" + historyRangeUrl;
            }

            System.err.println("#####################################");
            System.err.println("Url: " + url);
            System.err.println("#####################################");
            // Crear cabeceras para la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Usar ParameterizedTypeReference para especificar el tipo genérico
            ParameterizedTypeReference<RealEstateResponse> responseType
                    = new ParameterizedTypeReference<RealEstateResponse>() {
            };

            // Enviar la solicitud POST al endpoint del controlador
            ResponseEntity<RealEstateResponse> response = restTemplate.exchange(
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
//                this.addProperty(response.getBody());
            return response.getBody();
//            }
        } catch (RestClientException e) {
            throw new RuntimeException(e);
//            throw new BusinessNotFoundException(new GlobalBusinessException(
//                    DomainErrorMessage.BUSINESS_NOT_FOUND,
//                    new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())
//            ));
        }
    }
}
