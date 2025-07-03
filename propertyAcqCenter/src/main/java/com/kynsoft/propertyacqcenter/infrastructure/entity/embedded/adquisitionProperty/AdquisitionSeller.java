package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionSellerDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
public class AdquisitionSeller {
    @Column(name = "full_name", nullable = true)
    private String fullName;

    @Column(name = "entity_name", nullable = true)
    private String entityName;

    @Column(name = "articles_of_incorporation", nullable = true)
    private String articlesOfIncorporation;

    @Column(name = "certificate_of_good_standing", nullable = true)
    private String certificateOfGoodStanding;

    @Column(name = "operating_agreement", nullable = true)
    private String operatingAgreement;

    @Column(name = "type_of_ownership", nullable = true)
    private String ownershipType;

    @Column(name = "resolution_to_sell", nullable = true)
    private String resolutionToSell;

    // Información de contacto
    @Column(name = "contact_email", nullable = true)
    private String contactEmail;

    @Column(name = "mobile_phone", nullable = true)
    private String mobilePhone;

    @Column(name = "mailing_address", nullable = true)
    private String mailingAddress;

    // Información personal
    @Column(name = "social_security_number", nullable = true)
    private String socialSecurityNumber;

    @Column(name = "marital_status", nullable = true)
    private String maritalStatus;

    @Column(name = "government_id", nullable = true)
    private String governmentId;

    @Column(name = "w9_form", nullable = true)
    private String w9Form;

    // Información FIRPTA
    @Column(name = "is_foreign_seller", nullable = true)
    private Boolean isForeignSeller;

    @Column(name = "firpta_affidavit", nullable = true)
    private String firptaAffidavit;

    // Información bancaria
    @Column(name = "seller_wire_account_holder", nullable = true)
    private String sellerWireAccountHolder;

    @Column(name = "seller_wire_account_number", nullable = true)
    private String sellerWireAccountNumber;

    @Column(name = "seller_wire_routing_number", nullable = true)
    private String sellerWireRoutingNumber;

    @Column(name = "zelle_contact", nullable = true)
    private String zelleContact;

    public AdquisitionSeller(AdquisitionSellerDto dto) {
        this.fullName = dto.getFullName();
        this.entityName = dto.getEntityName();
        this.articlesOfIncorporation = dto.getArticlesOfIncorporation();
        this.certificateOfGoodStanding = dto.getCertificateOfGoodStanding();
        this.operatingAgreement = dto.getOperatingAgreement();
        this.ownershipType = dto.getOwnershipType();
        this.resolutionToSell = dto.getResolutionToSell();
        this.contactEmail = dto.getContactEmail();
        this.mobilePhone = dto.getMobilePhone();
        this.mailingAddress = dto.getMailingAddress();
        this.socialSecurityNumber = dto.getSocialSecurityNumber();
        this.maritalStatus = dto.getMaritalStatus();
        this.governmentId = dto.getGovernmentId();
        this.w9Form = dto.getW9Form();
        this.isForeignSeller = dto.getIsForeignSeller();
        this.firptaAffidavit = dto.getFirptaAffidavit();
        this.sellerWireAccountHolder = dto.getSellerWireAccountHolder();
        this.sellerWireAccountNumber = dto.getSellerWireAccountNumber();
        this.sellerWireRoutingNumber = dto.getSellerWireRoutingNumber();
        this.zelleContact = dto.getZelleContact();
    }

    public AdquisitionSellerDto toAggregate(){
        return AdquisitionSellerDto
                .builder()
                .fullName(fullName)
                .entityName(entityName)
                .articlesOfIncorporation(articlesOfIncorporation)
                .certificateOfGoodStanding(certificateOfGoodStanding)
                .operatingAgreement(operatingAgreement)
                .ownershipType(ownershipType)
                .resolutionToSell(resolutionToSell)
                .contactEmail(contactEmail)
                .mobilePhone(mobilePhone)
                .mailingAddress(mailingAddress)
                .socialSecurityNumber(socialSecurityNumber)
                .maritalStatus(maritalStatus)
                .governmentId(governmentId)
                .w9Form(w9Form)
                .isForeignSeller(isForeignSeller)
                .firptaAffidavit(firptaAffidavit)
                .sellerWireAccountHolder(sellerWireAccountHolder)
                .sellerWireAccountNumber(sellerWireAccountNumber)
                .sellerWireRoutingNumber(sellerWireRoutingNumber)
                .zelleContact(zelleContact)
                .build();
    }
}
