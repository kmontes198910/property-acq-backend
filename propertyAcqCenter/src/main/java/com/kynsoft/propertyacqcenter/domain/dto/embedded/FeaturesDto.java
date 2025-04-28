package com.kynsoft.propertyacqcenter.domain.dto.embedded;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeaturesDto {

    private boolean cooling;
    private String coolingType;
    private String exteriorType;
    private int floorCount;
    private String foundationType;
    private boolean garage;
    private int garageSpaces;
    private String garageType;
    private boolean pool;
    private String poolType;
    private String roofType;
    private int unitCount;
}
