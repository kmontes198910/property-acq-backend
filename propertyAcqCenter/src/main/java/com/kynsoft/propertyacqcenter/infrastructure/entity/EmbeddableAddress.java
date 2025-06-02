package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.EmbeddableAddressDto;
import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmbeddableAddress {
    private String streetAddress1;
    private String streetAddress2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "address_type")
    private AddressType addressType;

    public EmbeddableAddress(String streetAddress1, String streetAddress2, String city, String state, String zipCode, String country, AddressType addressType) {
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.addressType = addressType;
    }

    public EmbeddableAddress(EmbeddableAddressDto dto) {
        this.streetAddress1 = dto.getStreetAddress1();
        this.streetAddress2 = dto.getStreetAddress2();
        this.city = dto.getCity();
        this.state = dto.getState();
        this.zipCode = dto.getZipCode();
        this.country = dto.getCountry();
        this.addressType = dto.getAddressType();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
    }

    public EmbeddableAddressDto toAggregate() {
        return EmbeddableAddressDto.builder()
                .streetAddress1(streetAddress1)
                .streetAddress2(streetAddress2)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .country(country)
                .addressType(addressType)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
