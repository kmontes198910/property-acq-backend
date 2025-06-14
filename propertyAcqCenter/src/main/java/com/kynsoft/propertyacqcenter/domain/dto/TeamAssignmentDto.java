package com.kynsoft.propertyacqcenter.domain.dto;

import java.time.LocalDateTime;
import java.util.List;
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
    private LegalEntityDto buyerEntityName;
    private List<CompanyContactDto> buyerContactReps;
    private List<CompanyContactDto> titleEscrowCompany;
    private List<CompanyContactDto> lenderCompany;
    private List<CompanyContactDto> projectManager;
    private List<CompanyContactDto> legalContact;
    private PropertyDto property;
    private List<CompanyContactDto> seller;
    private List<CompanyContactDto> hoa;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
