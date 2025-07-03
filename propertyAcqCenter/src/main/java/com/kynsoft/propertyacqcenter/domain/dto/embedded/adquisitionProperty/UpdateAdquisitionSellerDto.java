package com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty;

import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAdquisitionSellerDto {
    private UUID idAdquisition;
    private String fullName;
    private String entityName;
    private String articlesOfIncorporation;
    private String certificateOfGoodStanding;
    private String operatingAgreement;
    private String ownershipType;
    private String resolutionToSell;
    private String contactEmail;
    private String mobilePhone;
    private String mailingAddress;
    private String socialSecurityNumber;
    private String maritalStatus;
    private String governmentId;
    private String w9Form;
    private Boolean isForeignSeller;
    private String firptaAffidavit;
    private String wireAccountHolder;
    private String wireAccountNumber;
    private String wireRoutingNumber;
    private String zelleContact;
}
