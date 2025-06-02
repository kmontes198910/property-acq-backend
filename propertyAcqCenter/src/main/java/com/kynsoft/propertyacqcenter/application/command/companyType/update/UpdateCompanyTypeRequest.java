package com.kynsoft.propertyacqcenter.application.command.companyType.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCompanyTypeRequest {

    private String name;
    private String code;
    private String description;
    private String examples;
    private Boolean isActive;
}
