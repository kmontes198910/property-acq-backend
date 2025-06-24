package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.domain.enums.CompanyType;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "sub_category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategory {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = true)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ContactType category;

    @Column(name = "company_type")
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    public SubCategory(SubCategoryDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.code = dto.getCode();
        this.description = dto.getDescription();
        this.category = dto.getCategory();
        this.companyType = dto.getCompanyType();
    }

    public SubCategoryDto toAggregate() {
        return SubCategoryDto.builder()
                .id(this.id)
                .name(name)
                .code(code)
                .description(description)
                .category(category)
                .companyType(companyType)
                .build();
    }
}