package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MortgageDto {

    private UUID id;
    private Double estimatedBalance;
    private UUID property;
}
