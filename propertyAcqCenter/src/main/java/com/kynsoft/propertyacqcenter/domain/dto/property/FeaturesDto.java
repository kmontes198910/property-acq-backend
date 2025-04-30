package com.kynsoft.propertyacqcenter.domain.dto.property;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeaturesDto {
    private String architectureType;
    private boolean cooling;
    private String coolingType;
    private String exteriorType;
    private boolean fireplace;
    private String fireplaceType;
    private int floorCount;
    private String foundationType;
    private boolean garage;
    private int garageSpaces;
    private String garageType;
    private boolean heating;
    private String heatingType;
    private boolean pool;
    private String poolType;
    private String roofType;
    private int roomCount;
    private int unitCount;
    private String viewType;
}
