package com.kynsoft.propertyacqcenter.infrastructure.services.http.estimateValue;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.propertyacqcenter.application.response.rentcast.EstimatedValueResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClientException;

@Service
public class RentCastEstimateValueServiceImpl {

    //TODO: esta api debe de ser cambiada por la real a consumir.
    @Value("${rentcast.api.key:956392a6c15d4dca8e25623f87c8121b}")
    private String apiKey;

//    private final String BASE_URL = "http://localhost:8097/api/rentcast/mock";
    //private final String BASE_URL = "http://property-acq-center-service:9901/api/rentcast/mock";
    private final String BASE_URL = "https://api.rentcast.io/v1";

    private final RestTemplate restTemplate;

    public RentCastEstimateValueServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpEntity<String> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        return new HttpEntity<>(headers);
    }

    //TODO: La response de este metodo, lo vamos a trasformar en la capa de application.
//    @Cacheable(value = "propertyCache", key = "#address", unless = "#result == null")
    public EstimatedValueResponse getEstimatedValue(String address,
                                                    String propertyType,
                                                    double latitude,
                                                    double longitude,
                                                    double bedrooms,
                                                    double bathrooms,
                                                    double squareFootage,
                                                    int daysOld) {
        try {
            String cleanedAddress = address.trim(); // Elimina espacios al inicio/final
            //verdadero
            String addressUrl = !address.equals("") ? "address=" + cleanedAddress : null;

            String latitudeUrl = latitude != -1 ? "latitude=" + latitude : null;
            String longitudeUrl = longitude != -1 ? "longitude=" + longitude : null;
            String bedroomsUrl = bedrooms != -1 ? "bedrooms=" + bedrooms : null;
            String bathroomsUrl = bathrooms != -1 ? "bathrooms=" + bathrooms : null;
            String squareFootageUrl = squareFootage != -1 ? "squareFootage=" + squareFootage : null;
            String daysOldUrl = daysOld != 0 ? "daysOld=" + daysOld : null;

            String url = BASE_URL + "/avm/value?propertyType=" + propertyType;
            //String url = BASE_URL + "/avm/value?address=" + cleanedAddress;
            //String url = BASE_URL + "/value/fake";

            if (addressUrl != null) {
                url = url + "&" + addressUrl;
            }
            if (latitudeUrl != null) {
                url = url + "&" + latitudeUrl;
            }
            if (longitudeUrl != null) {
                url = url + "&" + longitudeUrl;
            }
            if (bedroomsUrl != null) {
                url = url + "&" + bedroomsUrl;
            }
            if (bathroomsUrl != null) {
                url = url + "&" + bathroomsUrl;
            }
            if (daysOldUrl != null) {
                url = url + "&" + daysOldUrl;
            }
            if (squareFootageUrl != null) {
                url = url + "&" + squareFootageUrl;
            }
            System.err.println("##############################################");
            System.err.println("##############################################");
            System.err.println("url: " + url);
            System.err.println("##############################################");
            System.err.println("##############################################");
            // Crear cabeceras para la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Usar ParameterizedTypeReference para especificar el tipo genérico
            ParameterizedTypeReference<EstimatedValueResponse> responseType
                    = new ParameterizedTypeReference<EstimatedValueResponse>() {
            };

            // Enviar la solicitud POST al endpoint del controlador
            ResponseEntity<EstimatedValueResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    createHttpEntity(),
                    responseType);

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
