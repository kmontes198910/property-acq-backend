package com.kynsof.identity.infrastructure.services.test;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class RentCastServiceImpl implements IRentCastService {

    @Value("${rentcast.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://api.rentcast.io/v1";
    private final RestTemplate restTemplate = new RestTemplate();

    private HttpEntity<String> createHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Api-Key", apiKey);
        return new HttpEntity<>(headers);
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    @Override
    public List<PropertyResponse> getPropertyDetails(String address) {
        try {
            String cleanedAddress = address.trim(); // Elimina espacios al inicio/final
            String encodedAddress = URLEncoder.encode(cleanedAddress, StandardCharsets.UTF_8);
            String url = BASE_URL + "/properties?address=" + encodedAddress;

            ResponseEntity<PropertyResponse[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    createHttpEntity(),
                    PropertyResponse[].class
            );

            PropertyResponse[] body = response.getBody();

            if (body == null || body.length == 0) {
                return Collections.emptyList(); // o lanza una excepción personalizada si prefieres
            }

            return Arrays.asList(body);

        } catch (Exception e) {
            // Puedes loguear aquí si lo deseas
            throw new RuntimeException("Error fetching property details from RentCast API", e);
        }
    }

    @Override
    public EstimatedValueResponse getEstimatedValueDetail(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address.trim(), StandardCharsets.UTF_8);
            String url = BASE_URL + "/avm/value?address=" + encodedAddress;

            ResponseEntity<EstimatedValueResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    createHttpEntity(),
                    EstimatedValueResponse.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching estimated value details from RentCast API", e);
        }
    }

    @Override
    public RentEstimateResponse getRentEstimateDetail(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address.trim(), StandardCharsets.UTF_8);
            String url = BASE_URL + "/avm/rent/long-term?address=" + encodedAddress;

            ResponseEntity<RentEstimateResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    createHttpEntity(),
                    RentEstimateResponse.class
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching rent estimate from RentCast API", e);
        }
    }

    @Override
    public List<SaleListingDto> getSaleListings(String city, String state) {
        try {
            String encodedCity = URLEncoder.encode(city.trim(), StandardCharsets.UTF_8);
            String encodedState = URLEncoder.encode(state.trim(), StandardCharsets.UTF_8);

            String url = BASE_URL + "/listings/sale?city=" + encodedCity + "&state=" + encodedState;

            ResponseEntity<List<SaleListingDto>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    createHttpEntity(),
                    new ParameterizedTypeReference<List<SaleListingDto>>() {
                    }
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching sale listings from RentCast API", e);
        }

    }
}