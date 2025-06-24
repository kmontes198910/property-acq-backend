package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.CompanyType;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryDto {

    private UUID id;
    private String name;
    private String code;
    private String description;
    private ContactType category;
    private CompanyType companyType;
}