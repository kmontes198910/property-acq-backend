package com.kynsoft.propertyacqcenter.application.command.constructionType.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateConstructionTypeRequest {

    private String name;
    private String description;
    private String code;
    private Boolean isSpecialized;
    private String specializationArea;
    private Boolean requiresLicense;
    private Boolean isActive;
}
