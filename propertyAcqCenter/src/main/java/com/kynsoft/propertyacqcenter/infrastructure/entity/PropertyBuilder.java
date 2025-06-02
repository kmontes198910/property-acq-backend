package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.PropertyBuilderDto;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyBuilder {

    private String builderName;
    private String development;
    private String phone;
    private String website;

    public PropertyBuilder(PropertyBuilderDto dto) {
        this.builderName = dto.getName();
        this.development = dto.getDevelopment();
        this.phone = dto.getPhone();
        this.website = dto.getWebsite();
    }

    public PropertyBuilderDto toAggregate() {
        return PropertyBuilderDto.builder()
                .name(builderName)
                .development(development)
                .phone(phone)
                .website(website)
                .build();
    }
}