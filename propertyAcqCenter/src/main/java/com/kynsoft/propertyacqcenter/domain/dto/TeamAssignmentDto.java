package com.kynsoft.propertyacqcenter.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamAssignmentDto {
    private UUID id;
    private CompanyContactDto buyerEntityName;
    private CompanyContactDto buyerContactRep;
    private CompanyContactDto titleEscrowCompany;
    private CompanyContactDto lenderCompany;
    private CompanyContactDto projectManager;
    private CompanyContactDto legalContact;
    private PropertyDto property;
    private CompanyContactDto seller;
    private CompanyContactDto hoa;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
