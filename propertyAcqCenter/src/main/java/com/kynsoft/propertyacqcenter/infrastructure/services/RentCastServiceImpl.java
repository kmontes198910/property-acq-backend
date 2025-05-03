package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.propertyacqcenter.application.response.rentcast.EstimatedValueResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.RentEstimateResponse;
import com.kynsoft.propertyacqcenter.domain.enums.EventType;
import com.kynsoft.propertyacqcenter.domain.enums.OwnerType;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.services.IRentCastService;
import com.kynsoft.propertyacqcenter.application.response.rentcast.SaleListingResponse;
import com.kynsoft.propertyacqcenter.infrastructure.entity.*;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.PropertyDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;

@Service
public class RentCastServiceImpl implements IRentCastService {

    @Value("${rentcast.api.key:http://localhost:8097/api/rentcast/mock}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public RentCastServiceImpl(RestTemplate restTemplate) {
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

    @Override
    public List<PropertyDto> getPropertyDetails(String address) {
        try {
            String url = apiKey + "/property/fake";

            // Crear cabeceras para la solicitud
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Crear la entidad de la solicitud con el cuerpo (request) y las cabeceras
            HttpEntity<UUID> entity = new HttpEntity<>(UUID.randomUUID(), headers);

            // Enviar la solicitud POST al endpoint del controlador
            ResponseEntity<List> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, List.class);

            if (!HttpStatus.OK.equals(response.getStatusCode())) {
                throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())));
            }
            return response.getBody();
        } catch (RestClientException e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, new ErrorField("id", DomainErrorMessage.BUSINESS_NOT_FOUND.getReasonPhrase())));
        }
    }

    @Override
    public EstimatedValueResponse getEstimatedValueDetail(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address.trim(), StandardCharsets.UTF_8);

            //verdadero
            //String url = BASE_URL + "/avm/value?address=" + encodedAddress;

            //probar el servicio que esta en RentCastController /property/fake
            String url = "http://localhost:8097/api/rentcast/value/fake";

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

            //verdadero
            //String url = BASE_URL + "/avm/rent/long-term?address=" + encodedAddress;

            //probar el servicio que esta en RentCastController /property/fake
            String url = "http://localhost:8097/api/rentcast/rent/fake";

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
    public List<SaleListingResponse> getSaleListings(String city, String state) {
        try {
            String encodedCity = URLEncoder.encode(city.trim(), StandardCharsets.UTF_8);
            String encodedState = URLEncoder.encode(state.trim(), StandardCharsets.UTF_8);

            //verdadero
            //String url = BASE_URL + "/listings/sale?city=" + encodedCity + "&state=" + encodedState;

            //probar el servicio que esta en RentCastController /sales/fake
            String url = "http://localhost:8097/api/rentcast/sale/fake";

            ResponseEntity<List<SaleListingResponse>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    createHttpEntity(),
                    new ParameterizedTypeReference<List<SaleListingResponse>>() {
                    }
            );

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching sale listings from RentCast API", e);
        }

    }

    @Override
    public List<UUID> createRentCastProperty(List<PropertyResponse> propertyResponses) {
        // Crear la entidad Property a partir de PropertyResponse
        List<UUID> ids = new ArrayList<UUID>();
        for (PropertyResponse propertyResponse : propertyResponses) {
            Property property = Property.builder()
                    .id(UUID.randomUUID()) // Generar un nuevo UUID
                    .address(EmbeddableAddress.builder()
                            .streetAddress1(propertyResponse.getAddressLine1())
                            .streetAddress2(propertyResponse.getAddressLine2())
                            .city(propertyResponse.getCity())
                            .state(propertyResponse.getState())
                            .zipCode(propertyResponse.getZipCode())
                            .country(propertyResponse.getCounty())
                            .latitude(propertyResponse.getLatitude())
                            .longitude(propertyResponse.getLongitude())
                            .build())
                    .propertyType(PropertyType.fromString(propertyResponse.getPropertyType()))
                    .bedrooms(propertyResponse.getBedrooms())
                    .bathrooms(propertyResponse.getBathrooms())
                    .squareFeet(propertyResponse.getSquareFootage())
                    .lotSize(propertyResponse.getLotSize())
                    .yearBuilt(propertyResponse.getYearBuilt())
                    .lastSaleDate(propertyResponse.getLastSaleDate() != null ? OffsetDateTime.parse(propertyResponse.getLastSaleDate()).toLocalDate() : null)
                    .lastSalePrice((double) propertyResponse.getLastSalePrice())
                    .owner(propertyResponse.getOwner() != null ? Owner.builder()
                            .names(propertyResponse.getOwner().getNames().toString())
                            .type(propertyResponse.getOwner().getType() != null ? OwnerType.fromString(propertyResponse.getOwner().getType()) : null)
                            .build() : null)
                    .features(propertyResponse.getFeatures() != null ? Features.builder()
                            .cooling(propertyResponse.getFeatures().isCooling())
                            .coolingType(propertyResponse.getFeatures().getCoolingType())
                            .exteriorType(propertyResponse.getFeatures().getExteriorType())
                            .floorCount(propertyResponse.getFeatures().getFloorCount())
                            .foundationType(propertyResponse.getFeatures().getFoundationType())
                            .garage(propertyResponse.getFeatures().isGarage())
                            .garageSpaces(propertyResponse.getFeatures().getGarageSpaces())
                            .garageType(propertyResponse.getFeatures().getGarageType())
                            .pool(propertyResponse.getFeatures().isPool())
                            .poolType(propertyResponse.getFeatures().getPoolType())
                            .roofType(propertyResponse.getFeatures().getRoofType())
                            .unitCount(propertyResponse.getFeatures().getUnitCount())
                            .build() : null)
                    .taxAssessments(propertyResponse.getTaxAssessments() != null ? propertyResponse.getTaxAssessments().values().stream()
                            .map(tax -> TaxAssessment.builder()
                                    .id(UUID.randomUUID())
                                    .year(tax.getYear())
                                    .value(tax.getValue())
                                    .land(tax.getLand())
                                    .improvements(tax.getImprovements())
                                    .build())
                            .toList() : null)
                    .history(propertyResponse.getHistory() != null ? propertyResponse.getHistory().values().stream()
                            .map(history -> History.builder()
                                    .id(UUID.randomUUID())
                                    .date(history.getDate() != null ? OffsetDateTime.parse(history.getDate()).toLocalDateTime() : null)
                                    .price((double) history.getPrice())
                                    .event(EventType.fromValue(history.getEvent()))
                                    .build())
                            .toList() : null)
                    .build();

            // Persistir la entidad en la base de datos
            //property = repositoryCommand.save(property);
            ids.add(property.getId());
        }

        // Retornar el UUID de la propiedad creada
        return ids;
    }
}