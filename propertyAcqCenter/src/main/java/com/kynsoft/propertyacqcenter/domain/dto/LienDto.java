package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class LienDto {

    private UUID id;
    private double lienAmount;
    private boolean involuntary;
    private boolean active;
    private String documentType;
    private String taxType;
    private LocalDateTime taxDate;
    private UUID property;
}
