package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionSellerDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerAdquisitionPropertyResponse implements IResponse {

    private String fullName;
    private String entityName;
    private AdquisitionDocumentResponse articlesOfIncorporation;
    private AdquisitionDocumentResponse certificateOfGoodStanding;
    private AdquisitionDocumentResponse operatingAgreement;
    private String ownershipType;
    private AdquisitionDocumentResponse resolutionToSell;
    private String contactEmail;
    private String mobilePhone;
    private String mailingAddress;
    private String socialSecurityNumber;
    private String maritalStatus;
    private AdquisitionDocumentResponse governmentId;
    private AdquisitionDocumentResponse w9Form;
    private Boolean isForeignSeller;
    private AdquisitionDocumentResponse firptaAffidavit;
    private String sellerWireAccountHolder;
    private String sellerWireAccountNumber;
    private String sellerWireRoutingNumber;
    private String zelleContact;

    public SellerAdquisitionPropertyResponse(AdquisitionSellerDto dto) {
        this.fullName = dto.getFullName();
        this.entityName = dto.getEntityName();

        this.articlesOfIncorporation = DocumentMapper.mapDocumentField(dto.getArticlesOfIncorporation());
        this.certificateOfGoodStanding = DocumentMapper.mapDocumentField(dto.getCertificateOfGoodStanding());
        this.operatingAgreement = DocumentMapper.mapDocumentField(dto.getOperatingAgreement());

        this.ownershipType = dto.getOwnershipType();
        this.resolutionToSell = DocumentMapper.mapDocumentField(dto.getResolutionToSell());
        this.contactEmail = dto.getContactEmail();
        this.mobilePhone = dto.getMobilePhone();
        this.mailingAddress = dto.getMailingAddress();
        this.socialSecurityNumber = dto.getSocialSecurityNumber();
        this.maritalStatus = dto.getMaritalStatus();

        this.governmentId = DocumentMapper.mapDocumentField(dto.getGovernmentId());
        this.w9Form = DocumentMapper.mapDocumentField(dto.getW9Form());
        this.isForeignSeller = dto.getIsForeignSeller();

        this.firptaAffidavit = DocumentMapper.mapDocumentField(dto.getFirptaAffidavit());
        this.sellerWireAccountHolder = dto.getSellerWireAccountHolder();
        this.sellerWireAccountNumber = dto.getSellerWireAccountNumber();
        this.sellerWireRoutingNumber = dto.getSellerWireRoutingNumber();
        this.zelleContact = dto.getZelleContact();
    }

}