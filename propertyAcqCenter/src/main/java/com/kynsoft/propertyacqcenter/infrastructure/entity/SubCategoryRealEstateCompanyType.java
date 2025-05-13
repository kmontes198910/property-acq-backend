package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryRealEstateCompanyTypeDto;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "real_estate_company_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryRealEstateCompanyType {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public SubCategoryRealEstateCompanyType(SubCategoryRealEstateCompanyTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

    public SubCategoryRealEstateCompanyTypeDto toAggregate() {
        return SubCategoryRealEstateCompanyTypeDto.builder()
                .id(this.id)
                .name(name)
                .build();
    }
}