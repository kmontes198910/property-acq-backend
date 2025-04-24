package com.kynsoft.propertyacqcenter.application.command.constructionType.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateConstructionTypeRequest {

    private String name;
    private String description;
    private String code;
    private Boolean isSpecialized;
    private String specializationArea;
    private Boolean requiresLicense;
    private Boolean isActive;
}
