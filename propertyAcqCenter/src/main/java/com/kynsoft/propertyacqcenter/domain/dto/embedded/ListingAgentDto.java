package com.kynsoft.propertyacqcenter.domain.dto.embedded;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingAgentDto {

    private String name;
    private String phone;
    private String email;
    private String website;

}
