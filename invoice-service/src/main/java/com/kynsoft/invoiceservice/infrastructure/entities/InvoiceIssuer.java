package com.kynsoft.invoiceservice.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoice_issuers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceIssuer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    private String ruc;
    
    @Column(name = "business_name")
    private String businessName;
    
    @Column(name = "commercial_name")
    private String commercialName;
    
    @Column(name = "establishment_address")
    private String establishmentAddress;
    
    @Column(name = "establishment")
    private String establishment;

    private String obligationContability;

    private String address;
    
    @Column(name = "emission_point")
    private String emissionPoint;
    
    private String email;
    
    private String phone;
    
    @Column(name = "accounting_required")
    private String accountingRequired;
    
    @Column(name = "special_taxpayer")
    private String specialTaxpayer;
    
    @Column(name = "retention_agent")
    private String retentionAgent;
    
    @Column(name = "rimpe_regime")
    private String rimpeRegime;
    
    @Column(name = "logo_url")
    private String logoUrl;
    
    @Column(name = "environment", columnDefinition = "varchar(1)")
    private String environment = "1";
    
    @Column(name = "status")
    private Boolean status;
    
    @Column(name = "digital_cert_p12_path")
    private String digitalCertP12Path;
    
    @Column(name = "digital_cert_password")
    private String digitalCertPassword;
    
    @Column(length = 2000)
    private String description;
    
    @OneToMany(mappedBy = "invoiceIssuer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InvoiceIssuingSequence> sequences = new ArrayList<>();
    
    // Helper methods for bidirectional relationships
    public void addSequence(InvoiceIssuingSequence sequence) {
        sequences.add(sequence);
        sequence.setInvoiceIssuer(this);
    }
    
    public void removeSequence(InvoiceIssuingSequence sequence) {
        sequences.remove(sequence);
        sequence.setInvoiceIssuer(null);
    }

}