package com.kynsoft.propertyacqcenter.domain.dto.embedded;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyBuilderDto {

    private String name;
    private String development;
    private String phone;
    private String website;
}
