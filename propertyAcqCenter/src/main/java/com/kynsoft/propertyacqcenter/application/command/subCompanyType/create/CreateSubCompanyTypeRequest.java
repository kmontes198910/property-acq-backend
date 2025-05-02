package com.kynsoft.propertyacqcenter.application.command.subCompanyType.create;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSubCompanyTypeRequest {

    private String name;
    private UUID companyType;
    private String description;
    private String code;
    private Boolean isSpecialized;
    private String specializationArea;
    private Boolean requiresLicense;
    private Boolean isActive;
}
