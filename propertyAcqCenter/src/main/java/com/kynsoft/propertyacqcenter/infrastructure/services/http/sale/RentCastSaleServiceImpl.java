package com.kynsoft.propertyacqcenter.infrastructure.services.http.sale;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.propertyacqcenter.application.response.rentcast.SaleListingResponse;
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
public class RentCastSaleServiceImpl {

    @Value("${rentcast.api.key:956392a6c15d4dca8e25623f87c8121b}")
    private String apiKey;

    //TODO: esta api debe de ser cambiada por la real a consumir.
    private final String BASE_URL = "https://api.rentcast.io/v1";
    //private final String BASE_URL = "http://localhost:8097/api/rentcast/mock";

    private final RestTemplate restTemplate;

    public RentCastSaleServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
    public List<SaleListingResponse> getRentEstimate(String address,
                                                     String propertyType,
                                                     String city,
                                                     String state,
                                                     String zipCode,
                                                     double latitude,
                                                     double longitude,
                                                     double radius,
                                                     double bedrooms,
                                                     double bathrooms,
                                                     String status,
                                                     int daysOld) {
        try {
            String cleanedAddress = address.trim(); // Elimina espacios al inicio/final
            //String url = BASE_URL + "/sale/fake";

            String addressUrl = !address.equals("") ? "address=" + cleanedAddress : null;
            String propertyTypeUrl = !propertyType.equals("") ? "propertyType=" + propertyType : null;
            String cityUrl = !propertyType.equals("") ? "city=" + city : null;
            String stateUrl = !state.equals("") ? "state=" + state : null;
            String zipCodeUrl = !zipCode.equals("") ? "zipCode=" + zipCode : null;

            String latitudeUrl = latitude != -1 ? "latitude=" + latitude : null;
            String longitudeUrl = longitude != -1 ? "longitude=" + longitude : null;
            String radiusUrl = radius != -1 ? "radius=" + radius : null;
            String bedroomsUrl = bedrooms != -1 ? "bedrooms=" + bedrooms : null;
            String bathroomsUrl = bathrooms != -1 ? "bathrooms=" + bathrooms : null;
            String daysOldUrl = daysOld != 0 ? "daysOld=" + daysOld : null;

            //verdadero
            //String url = BASE_URL + "/listings/sale?address=" + cleanedAddress;
            String url = BASE_URL + "/listings/sale?status=" + status;
            if (addressUrl != null) {
                url = url + "&" + addressUrl;
            }
            if (propertyTypeUrl != null) {
                url = url + "&" + propertyTypeUrl;
            }
            if (cityUrl != null) {
                url = url + "&" + cityUrl;
            }
            if (stateUrl != null) {
                url = url + "&" + stateUrl;
            }
            if (zipCodeUrl != null) {
                url = url + "&" + zipCodeUrl;
            }
            if (latitudeUrl != null) {
                url = url + "&" + latitudeUrl;
            }
            if (longitudeUrl != null) {
                url = url + "&" + longitudeUrl;
            }
            if (radiusUrl != null) {
                url = url + "&" + radiusUrl;
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

            System.err.println("##############################################");
            System.err.println("##############################################");
            System.err.println("url: " + url);
            System.err.println("##############################################");
            System.err.println("##############################################");
            // Crear cabeceras para la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Usar ParameterizedTypeReference para especificar el tipo genérico
            ParameterizedTypeReference<List<SaleListingResponse>> responseType
                    = new ParameterizedTypeReference<List<SaleListingResponse>>() {
            };

            // Enviar la solicitud POST al endpoint del controlador
            ResponseEntity<List<SaleListingResponse>> response = restTemplate.exchange(
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
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.BUSINESS_NOT_FOUND,
                    new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())
            ));
        }
    }
}
