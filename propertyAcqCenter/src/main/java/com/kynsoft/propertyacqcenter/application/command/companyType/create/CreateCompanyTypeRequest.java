package com.kynsoft.propertyacqcenter.application.command.companyType.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyTypeRequest {

    private String name;
    private String code;
    private String description;
    private String examples;
    private Boolean isActive;
}
