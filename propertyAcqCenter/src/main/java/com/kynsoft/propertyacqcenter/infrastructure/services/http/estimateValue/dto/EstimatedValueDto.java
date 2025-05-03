package com.kynsoft.propertyacqcenter.infrastructure.services.http.estimateValue.dto;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EstimatedValueDto {
    private UUID id;
    private Integer price;
    private Integer priceRangeLow;
    private Integer priceRangeHigh;
    private Double latitude;
    private Double longitude;
    private List<ComparablePropertyDto> comparables;
}