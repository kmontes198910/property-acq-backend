package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Builder
@NoArgsConstructor
public class SellerDocumentsDto {
    private boolean sellerEntityDocumentation;
    private boolean articlesOfIncorporation;
    private boolean certificateOfGoodStanding;
    private boolean operatingAgreement;
    private boolean w9Form;
    private boolean resolutionToSell;
    private boolean proofOfOwnership;
    private boolean leaseAgreements;
    private boolean firptaAffidavit;
    private boolean payoffStatement;
}
