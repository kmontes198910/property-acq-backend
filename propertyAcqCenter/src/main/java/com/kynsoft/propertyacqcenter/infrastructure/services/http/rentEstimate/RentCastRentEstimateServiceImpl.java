package com.kynsoft.propertyacqcenter.infrastructure.services.http.rentEstimate;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.propertyacqcenter.application.response.rentcast.RentEstimateResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClientException;

@Service
public class RentCastRentEstimateServiceImpl {

    //TODO: esta api debe de ser cambiada por la real a consumir.
    @Value("${rentcast.api.key:956392a6c15d4dca8e25623f87c8121b}")
    private String apiKey;

    //private final String BASE_URL = "http://localhost:8097/api/rentcast/mock";
    private final String BASE_URL = "https://api.rentcast.io/v1";

    private final RestTemplate restTemplate;

    public RentCastRentEstimateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpEntity<String> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        return new HttpEntity<>(headers);
    }

    //TODO: La response de este metodo, lo vamos a trasformar en la capa de application.
    //@Cacheable(value = "propertyCache", key = "#address", unless = "#result == null")
    public RentEstimateResponse getRentEstimate(String address,
                                                String propertyType,
                                                double latitude,
                                                double longitude,
                                                double bedrooms,
                                                double bathrooms,
                                                double squareFootage,
                                                int daysOld,
                                                double maxRadius,
                                                int compCount) {
        try {
            String cleanedAddress = address.trim(); // Elimina espacios al inicio/final
            //String url = BASE_URL + "/rent/fake";

            String addressUrl = !address.equals("") ? "address=" + cleanedAddress : null;
            String propertyTypeUrl = !propertyType.equals("") ? "propertyType=" + propertyType : null;

            String latitudeUrl = latitude != -1 ? "latitude=" + latitude : null;
            String longitudeUrl = longitude != -1 ? "longitude=" + longitude : null;
            String bedroomsUrl = bedrooms != -1 ? "bedrooms=" + bedrooms : null;
            String maxRadiusUrl = maxRadius != -1 ? "maxRadius=" + maxRadius : null;
            String bathroomsUrl = bathrooms != -1 ? "bathrooms=" + bathrooms : null;
            String squareFootageUrl = squareFootage != -1 ? "squareFootage=" + squareFootage : null;
            String daysOldUrl = daysOld != 0 ? "daysOld=" + daysOld : null;

            //verdadero
            String url = BASE_URL + "/avm/rent/long-term?compCount=" + compCount;
            //String url = BASE_URL + "/avm/rent/long-term?address=" + cleanedAddress;
            if (propertyTypeUrl != null) {
                url = url + "&" + propertyTypeUrl;
            }
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
            if (maxRadiusUrl != null) {
                url = url + "&" + maxRadiusUrl;
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
            ParameterizedTypeReference<RentEstimateResponse> responseType
                    = new ParameterizedTypeReference<RentEstimateResponse>() {
            };

            // Enviar la solicitud POST al endpoint del controlador
            ResponseEntity<RentEstimateResponse> response = restTemplate.exchange(
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
