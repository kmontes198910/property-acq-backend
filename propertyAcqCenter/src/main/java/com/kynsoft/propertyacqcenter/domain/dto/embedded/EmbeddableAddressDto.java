package com.kynsoft.propertyacqcenter.domain.dto.embedded;

import com.kynsoft.propertyacqcenter.domain.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmbeddableAddressDto {
    private String streetAddress1;  // Se mapeará a address_street
    private String streetAddress2;  // Se mapeará a address_street
    private String city;    // address_city
    private String state;   // address_state
    private String zipCode; // address_zip_code
    private String country;
    private AddressType addressType;
    private Double latitude;
    private Double longitude;

    public EmbeddableAddressDto(String streetAddress1, String streetAddress2, String city, String state, String zipCode, String country, AddressType addressType) {
        this.streetAddress1 = streetAddress1;
        this.streetAddress2 = streetAddress2;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.addressType = addressType;
    }
}
