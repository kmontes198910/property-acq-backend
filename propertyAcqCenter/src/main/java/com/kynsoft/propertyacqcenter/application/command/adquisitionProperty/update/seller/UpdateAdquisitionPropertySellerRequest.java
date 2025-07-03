package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.seller;

import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAdquisitionPropertySellerRequest {

    private String fullName;
    private String entityName;
    private CreateDocumentRequest articlesOfIncorporation;//
    private CreateDocumentRequest certificateOfGoodStanding;//
    private CreateDocumentRequest operatingAgreement;//
    private String ownershipType;
    private CreateDocumentRequest resolutionToSell;//
    private String contactEmail;
    private String mobilePhone;
    private String mailingAddress;
    private String socialSecurityNumber;
    private String maritalStatus;
    private CreateDocumentRequest governmentId;//
    private CreateDocumentRequest w9Form;//
    private Boolean isForeignSeller;
    private CreateDocumentRequest firptaAffidavit;//
    private String sellerWireAccountHolder;
    private String sellerWireAccountNumber;
    private String sellerWireRoutingNumber;
    private String zelleContact;
}
