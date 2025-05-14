package com.kynsoft.propertyacqcenter.domain.dto;

import java.util.List;
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
    private List<OwnerDocumentDto> ownerDocuments;
}