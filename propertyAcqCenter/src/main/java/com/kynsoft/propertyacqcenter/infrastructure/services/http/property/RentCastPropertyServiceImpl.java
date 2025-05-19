package com.kynsoft.propertyacqcenter.infrastructure.services.http.property;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.infrastructure.entity.externalApi.ExternalProperty;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ExternalPropertyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ExternalPropertyReadDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.services.mock.RentCastServiceMockImpl;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClientException;

@Service
public class RentCastPropertyServiceImpl {

    //TODO: esta api debe de ser cambiada por la real a consumir.
    @Value("${rentcast.api.key:956392a6c15d4dca8e25623f87c8121b}")
    private String apiKey;

    private final String BASE_URL = "http://localhost:8097/api/rentcast/mock";
//    private final String BASE_URL = "http://property-acq-center-service:9901/api/rentcast/mock";
//    private final String BASE_URL = "https://api.rentcast.io/v1";

    private final RestTemplate restTemplate;
    private final RentCastServiceMockImpl resCastServiceMockImpl;
    private final ExternalPropertyWriteDataJPARepository repoCommand;
    private final ExternalPropertyReadDataJPARepository repoQuery;

    public RentCastPropertyServiceImpl(RestTemplate restTemplate,
            RentCastServiceMockImpl resCastServiceMockImpl,
            ExternalPropertyWriteDataJPARepository repoCommand,
            ExternalPropertyReadDataJPARepository repoQuery) {
        this.restTemplate = restTemplate;
        this.resCastServiceMockImpl = resCastServiceMockImpl;
        this.repoCommand = repoCommand;
        this.repoQuery = repoQuery;
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
    @Cacheable(value = "propertyCache", key = "#address", unless = "#result == null")
    public List<PropertyResponse> getPropertyDetails(String address) {
        try {
            String cleanedAddress = address.trim(); // Elimina espacios al inicio/final
            String encodedAddress = URLEncoder.encode(cleanedAddress, StandardCharsets.UTF_8);

//            Optional<ExternalProperty> property = this.repoQuery.findByPropertyId(address);
//            if (property.isPresent()) {
//                return this.createResponse(property);
//            } else {
            //verdadero
//            String url = BASE_URL + "/properties?address=" + cleanedAddress;
                String url = BASE_URL + "/property/fake";

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

    private List<PropertyResponse> createResponse(Optional<ExternalProperty> property) {
        List<PropertyResponse> list = new ArrayList<>();
        PropertyResponse pr = new PropertyResponse();
        pr.setId(property.get().getPropertyId());
        pr.setAddressLine1(property.get().getAddressLine1());
        pr.setAddressLine2(property.get().getAddressLine2());
        pr.setAssessorID(property.get().getAssessorID());
        pr.setBathrooms(property.get().getBathrooms());
        pr.setBedrooms(property.get().getBedrooms());
        pr.setCity(property.get().getCity());
        pr.setCounty(property.get().getCounty());
        pr.setPropertyType(property.get().getPropertyType());
        pr.setFeatures(new PropertyResponse.Features(
                property.get().getFeatures().isCooling(),
                property.get().getFeatures().getCoolingType(),
                property.get().getFeatures().getExteriorType(),
                property.get().getFeatures().getFloorCount(),
                property.get().getFeatures().getFoundationType(),
                property.get().getFeatures().isGarage(),
                property.get().getFeatures().getGarageSpaces(),
                property.get().getFeatures().getGarageType(),
                property.get().getFeatures().isPool(),
                property.get().getFeatures().getPoolType(),
                property.get().getFeatures().getRoofType(),
                property.get().getFeatures().getUnitCount()
        ));
        pr.setFormattedAddress(property.get().getFormattedAddress());
        pr.setHistory(property.get().getHistory().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new PropertyResponse.History(
                                entry.getValue().getEvent(),
                                entry.getValue().getDate(),
                                entry.getValue().getPrice()
                        )
                )));
        pr.setHoa(new PropertyResponse.HOA(property.get().getHoa().getFee()));
        pr.setLastSaleDate(property.get().getLastSaleDate());
        pr.setLastSalePrice(property.get().getLastSalePrice());
        pr.setLatitude(property.get().getLatitude());
        pr.setLongitude(property.get().getLongitude());
        pr.setLegalDescription(property.get().getLegalDescription());
        pr.setLotSize(property.get().getLotSize());
        pr.setOwner(new PropertyResponse.Owner(
                property.get().getOwner().getNames(),
                property.get().getOwner().getType(),
                new PropertyResponse.MailingAddress(
                        property.get().getOwner().getMailingAddress().getFormattedAddress(),
                        property.get().getOwner().getMailingAddress().getFormattedAddress(),
                        property.get().getOwner().getMailingAddress().getAddressLine1(),
                        property.get().getOwner().getMailingAddress().getAddressLine2(),
                        property.get().getOwner().getMailingAddress().getCity(),
                        property.get().getOwner().getMailingAddress().getState(),
                        property.get().getOwner().getMailingAddress().getZipCode()
                )));
        pr.setOwnerOccupied(property.get().isOwnerOccupied());
        pr.setPropertyTaxes(property.get().getPropertyTaxes().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new PropertyResponse.PropertyTax(
                                entry.getValue().getYear(),
                                entry.getValue().getTotal()
                        )
                )));
        pr.setSquareFootage(property.get().getSquareFootage());
        pr.setState(property.get().getState());
        pr.setSubdivision(property.get().getSubdivision());
        pr.setTaxAssessments(property.get().getTaxAssessments().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new PropertyResponse.TaxAssessment(
                                entry.getValue().getYear(),
                                entry.getValue().getValue(),
                                entry.getValue().getLand(),
                                entry.getValue().getImprovements()
                        )
                )));
        pr.setYearBuilt(property.get().getYearBuilt());
        pr.setZipCode(property.get().getZipCode());
        pr.setZoning(property.get().getZoning());

        list.add(pr);
        return list;
    }

    private void addProperty(List<PropertyResponse> response) {
        PropertyResponse p = response.get(0);
        this.repoCommand.save(new ExternalProperty(p));
    }
}
