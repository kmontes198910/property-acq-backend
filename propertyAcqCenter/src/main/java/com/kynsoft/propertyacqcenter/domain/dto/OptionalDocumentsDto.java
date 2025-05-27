package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OptionalDocumentsDto {
    private boolean has1031Exchange;
    private String exchangeAgreement;
    private String assignmentDocument;
    private String qualifiedIntermediaryInfo;

    // Poder notarial
    private boolean hasPowerOfAttorney;
    private String powerOfAttorneyDocument;
    private boolean titleCompanyApprovedPOA;

    // Documentos de sucesión o patrimonio
    private boolean hasProbateDocuments;
    private String lettersTestamentary;
    private String deathCertificate;

    // Órdenes judiciales
    private boolean hasCourtOrders;
    private String courtOrderDescription;

    // Informes ambientales
    private boolean hasEnvironmentalReports;
    private String phaseIReport;
    private String phaseIIReport;

    // Certificados de arrendamiento
    private boolean hasLeaseEstoppelCertificates;
    private int numberOfTenants;

    // Información comercial
    private boolean hasCommercialTenantRentRoll;
    private String rentRollDocument;

    // Zonificación
    private boolean hasZoningVerification;
    private String zoningVerificationLetter;
}
