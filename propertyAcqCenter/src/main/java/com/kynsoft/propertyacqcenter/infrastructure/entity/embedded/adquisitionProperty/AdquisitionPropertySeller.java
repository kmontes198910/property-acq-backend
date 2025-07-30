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
@Table(name = "adquisition_property_seller")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertySeller {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "adquisition_property_id")
    private AdquisitionProperty adquisitionProperty; // Relación bidireccional

    @Column(name = "seller_p_account_holder_name", nullable = true)
    private String sellerPersonalAccountHolderName;

    @Column(name = "seller_p_account_number", nullable = true)
    private String sellerPersonalAccountNumber;

    @Column(name = "seller_p_routing_number", nullable = true)
    private String sellerPersonalRoutingNumber;

    @Column(name = "seller_p_zelle_email_or_phone", nullable = true)
    private String sellerPersonalZelleEmailorPhone;

    @Column(name = "seller_p_bank_name", nullable = true)
    private String sellerPersonalBankName;

    @Column(name = "seller_bank_statement_request", nullable = true)
    private String sellerBankStatementRequest;//array

    @Column(name = "seller_p_bank_statements", nullable = true)
    private String sellerPersonalBankStatements;//array

    @Column(name = "seller_void_check", nullable = true)
    private Boolean sellerVoidCheck;

    @Column(name = "seller_p_void_check", nullable = true)
    private Boolean sellerPersonalVoidCheck;
}
