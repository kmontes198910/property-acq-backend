package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.seller;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateAdquisitionPropertySellerCommand implements ICommand {

    private UUID adquisitionId;
    private String fullName;
    private String entityName;
    private CreateDocumentRequest articlesOfIncorporation;
    private CreateDocumentRequest certificateOfGoodStanding;
    private CreateDocumentRequest operatingAgreement;
    private String ownershipType;
    private CreateDocumentRequest resolutionToSell;
    private String contactEmail;
    private String mobilePhone;
    private String mailingAddress;
    private String socialSecurityNumber;
    private String maritalStatus;
    private CreateDocumentRequest governmentId;
    private CreateDocumentRequest w9Form;
    private Boolean isForeignSeller;
    private CreateDocumentRequest firptaAffidavit;
    private String sellerWireAccountHolder;
    private String sellerWireAccountNumber;
    private String sellerWireRoutingNumber;
    private String zelleContact;

    public UpdateAdquisitionPropertySellerCommand(UUID adquisitionId, String fullName, String entityName, CreateDocumentRequest articlesOfIncorporation, 
                                                  CreateDocumentRequest certificateOfGoodStanding, CreateDocumentRequest operatingAgreement, 
                                                  String ownershipType, CreateDocumentRequest resolutionToSell, String contactEmail, String mobilePhone, 
                                                  String mailingAddress, String socialSecurityNumber, String maritalStatus, CreateDocumentRequest governmentId, 
                                                  CreateDocumentRequest w9Form, Boolean isForeignSeller, CreateDocumentRequest firptaAffidavit, String wireAccountHolder, 
                                                  String wireAccountNumber, String wireRoutingNumber, String zelleContact) {
        this.adquisitionId = adquisitionId;
        this.fullName = fullName;
        this.entityName = entityName;
        this.articlesOfIncorporation = articlesOfIncorporation;
        this.certificateOfGoodStanding = certificateOfGoodStanding;
        this.operatingAgreement = operatingAgreement;
        this.ownershipType = ownershipType;
        this.resolutionToSell = resolutionToSell;
        this.contactEmail = contactEmail;
        this.mobilePhone = mobilePhone;
        this.mailingAddress = mailingAddress;
        this.socialSecurityNumber = socialSecurityNumber;
        this.maritalStatus = maritalStatus;
        this.governmentId = governmentId;
        this.w9Form = w9Form;
        this.isForeignSeller = isForeignSeller;
        this.firptaAffidavit = firptaAffidavit;
        this.sellerWireAccountHolder = wireAccountHolder;
        this.sellerWireAccountNumber = wireAccountNumber;
        this.sellerWireRoutingNumber = wireRoutingNumber;
        this.zelleContact = zelleContact;
    }

    public static UpdateAdquisitionPropertySellerCommand fromRequest(UpdateAdquisitionPropertySellerRequest request, UUID adquisitionId) {
        return new UpdateAdquisitionPropertySellerCommand(
                adquisitionId,
                request.getFullName(),
                request.getEntityName(),
                request.getArticlesOfIncorporation(),
                request.getCertificateOfGoodStanding(),
                request.getOperatingAgreement(),
                request.getOwnershipType(),
                request.getResolutionToSell(),
                request.getContactEmail(),
                request.getMobilePhone(),
                request.getMailingAddress(),
                request.getSocialSecurityNumber(),
                request.getMaritalStatus(),
                request.getGovernmentId(),
                request.getW9Form(),
                request.getIsForeignSeller(),
                request.getFirptaAffidavit(),
                request.getSellerWireAccountHolder(),
                request.getSellerWireAccountNumber(),
                request.getSellerWireRoutingNumber(),
                request.getZelleContact()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateAdquisitionPropertySellerMessage(adquisitionId);
    }
}
