package com.kynsoft.propertyacqcenter.domain.dto.property.saleListing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListingAgentDto {

    private String name;
    private String phone;
    private String email;
    private String website;
}
