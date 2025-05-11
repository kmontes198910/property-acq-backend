package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceDto {

    private UUID id;
    private String insuranceType;
    private String document;
    private LegalEntityDto legalEntity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long daysSinceCreated;
    private long daysUntilSixty;
    private String fileName;
}
