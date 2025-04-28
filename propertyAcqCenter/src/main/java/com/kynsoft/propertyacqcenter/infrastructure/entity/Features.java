package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.FeaturesDto;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Features {

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

    public Features(FeaturesDto featuresDto) {
        this.cooling = featuresDto.isCooling();
        this.coolingType = featuresDto.getCoolingType();
        this.exteriorType = featuresDto.getExteriorType();
        this.floorCount = featuresDto.getFloorCount();
        this.foundationType = featuresDto.getFoundationType();
        this.garage = featuresDto.isGarage();
        this.garageSpaces = featuresDto.getGarageSpaces();
        this.garageType = featuresDto.getGarageType();
        this.pool = featuresDto.isPool();
        this.poolType = featuresDto.getPoolType();
        this.roofType = featuresDto.getRoofType();
        this.unitCount = featuresDto.getUnitCount();
    }

    public FeaturesDto toAggregate() {
        return FeaturesDto.builder()
                .cooling(cooling)
                .coolingType(coolingType)
                .exteriorType(exteriorType)
                .floorCount(floorCount)
                .foundationType(foundationType)
                .garage(garage)
                .garageSpaces(garageSpaces)
                .garageType(garageType)
                .pool(pool)
                .poolType(poolType)
                .roofType(roofType)
                .unitCount(unitCount)
                .build();
    }
}
