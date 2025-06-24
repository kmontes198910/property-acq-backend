package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionTitleCompanyDto;
import java.time.LocalDate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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

    private LocalDate dateAndTimeForInspections;
    private String instructionsForAccess;
    private LocalDate hoaBuyerInterviewDate;
    private LocalDate preferredMoveinDate;
    private String eSignAuthorization;
    private LocalDate finalWalkthroughDate;
    private String wireAccountHolderName;
    private String wireAccountNumber;
    private String wireRoutingNumber;
    private String zelleEmailorPhone;
    private String electricProviderConfirmation;
    private String gasServiceConfirmation;
    private String trashServiceConfirmation;
    private String waterSewerSetupConfirmation;

    private List<GeneralDocumentDto> documents;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    private AdquisitionTitleCompanyDto titleCompany;
}