package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty;

import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adquisition_property_buyer_personal_bank_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyBuyerPersonalBankInfo {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "adquisition_property_id")
    private AdquisitionProperty adquisitionProperty; // Relación bidireccional

    @Column(name = "account_holder_name", nullable = true)
    private String buyerPersonalAccountHolderName;

    @Column(name = "account_name", nullable = true)
    private String buyerPersonalAccountNumber;

    @Column(name = "routing_number", nullable = true)
    private String buyerPersonalRoutingNumber;

    @Column(name = "zelle_email_or_phone", nullable = true)
    private String buyerPersonalZelleEmailorPhone;

    @Column(name = "bank_statements", nullable = true)
    private String buyerPersonalBankStatements;

    @Column(name = "bank_name", nullable = true)
    private String buyerPersonalBankName;

    @Column(name = "use_for_hoa_bank_reference", nullable = true)
    private Boolean buyerPersonalUseForHoaBankReference;

    @Column(name = "use_legal_entity_for_hoa_bank_reference", nullable = true)
    private Boolean buyerLegalEntityUseForHoaBankReference;

    @Column(name = "void_check", nullable = true)
    private Boolean buyerVoidCheck;

    @Column(name = "use_for_lender_bank_reference", nullable = true)
    private Boolean buyerPersonalUseForLenderBankReference;

}
