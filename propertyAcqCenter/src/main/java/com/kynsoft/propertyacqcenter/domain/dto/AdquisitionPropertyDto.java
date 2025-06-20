package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyDto {

    private UUID id;
    private LegalEntityDto buyer;
    private PropertyDto property;
    private CompanyContactDto contact;
    private String buyerNameAndYearVehicle;
    private String buyerLicenseTagNo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}