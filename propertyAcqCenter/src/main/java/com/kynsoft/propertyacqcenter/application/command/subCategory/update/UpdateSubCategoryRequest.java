package com.kynsoft.propertyacqcenter.application.command.subCategory.update;

import com.kynsoft.propertyacqcenter.domain.enums.CompanyType;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubCategoryRequest {

    private String name;
    private String code;
    private String description;
    private ContactType category;
    private CompanyType companyType;
}
