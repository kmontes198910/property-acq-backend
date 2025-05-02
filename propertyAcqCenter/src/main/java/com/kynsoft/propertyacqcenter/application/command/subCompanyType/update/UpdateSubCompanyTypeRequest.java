package com.kynsoft.propertyacqcenter.application.command.subCompanyType.update;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubCompanyTypeRequest {

    private String name;
    private UUID companyType;
    private String description;
    private String code;
    private Boolean isSpecialized;
    private String specializationArea;
    private Boolean requiresLicense;
    private Boolean isActive;
}
