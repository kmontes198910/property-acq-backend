package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsoft.propertyacqcenter.application.response.rentcast.EstimatedValueResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.RentEstimateResponse;
import com.kynsoft.propertyacqcenter.domain.enums.EventType;
import com.kynsoft.propertyacqcenter.domain.enums.OwnerType;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.domain.services.IRentCastService;
import com.kynsoft.propertyacqcenter.application.response.rentcast.SaleListingResponse;
import com.kynsoft.propertyacqcenter.infrastructure.entity.*;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.PropertyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.PropertyReadDataJPARepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class RentCastServiceImpl implements IRentCastService {

    private final PropertyWriteDataJPARepository repositoryCommand;

    private final PropertyReadDataJPARepository repositoryQuery;

    @Value("${rentcast.api.key}")
    private String apiKey;

    private final String BASE_URL = "https://api.rentcast.io/v1";
    private final RestTemplate restTemplate = new RestTemplate();

    public RentCastServiceImpl(PropertyWriteDataJPARepository repositoryCommand, PropertyReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
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
    public List<PropertyResponse> getPropertyDetails(String address) {
        try {
            String cleanedAddress = address.trim(); // Elimina espacios al inicio/final
            String encodedAddress = URLEncoder.encode(cleanedAddress, StandardCharsets.UTF_8);

            //verdadero
            //String url = BASE_URL + "/properties?address=" + encodedAddress;

            //probar el servicio que esta en RentCastController /property/fake
            String url = "http://localhost:8097/api/rentcast/property/fake";

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
            property = repositoryCommand.save(property);
            ids.add(property.getId());
        }

        // Retornar el UUID de la propiedad creada
        return ids;
    }
}