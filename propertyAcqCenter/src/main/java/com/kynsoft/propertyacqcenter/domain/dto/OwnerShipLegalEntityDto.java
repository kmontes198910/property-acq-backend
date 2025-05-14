package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerShipLegalEntityDto {

    private UUID id;
    private String name;
    private Double ownershipPercentage;
    private LegalEntityDto legalEntity;
}