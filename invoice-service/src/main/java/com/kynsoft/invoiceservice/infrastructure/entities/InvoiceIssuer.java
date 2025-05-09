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
    
    @Column(name = "headquarters_address")
    private String headquartersAddress;
    
    @Column(name = "establishment_address")
    private String establishmentAddress;
    
    @Column(name = "establishment")
    private String establishment;
    
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
    
    @Column(name = "environment", columnDefinition = "varchar(1) default '1'")
    private String environment;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "sri_user")
    private String sriUser;
    
    @Column(name = "sri_password")
    private String sriPassword;
    
    @Column(name = "digital_cert_path")
    private String digitalCertPath;
    
    @Column(name = "digital_cert_password")
    private String digitalCertPassword;
    
    @Column(length = 2000)
    private String description;
    
    @OneToMany(mappedBy = "invoiceIssuer", cascade = CascadeType.ALL, orphanRemoval = true)
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