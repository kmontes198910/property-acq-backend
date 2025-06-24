package com.kynsoft.propertyacqcenter.application.response;

import com.kynsoft.propertyacqcenter.domain.dto.SubCategoryDto;
import com.kynsoft.propertyacqcenter.domain.enums.CompanyType;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryResponse {

    private UUID id;
    private String name;
    private String code;
    private String description;
    private ContactType category;
    private CompanyType companyType;

    public SubCategoryResponse(SubCategoryDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.code = dto.getCode();
        this.description = dto.getDescription();
        this.category = dto.getCategory();
        this.companyType = dto.getCompanyType();
    }

}