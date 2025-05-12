package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoice_issuers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InvoiceIssuer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String ruc;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "commercial_name")
    private String commercialName;

    // Agregando el campo moneda
    @Column(name = "currency")
    private String currency;

    @Column(name = "establishment")
    private String establishment;

    @Column(name = "point_of_sale")
    private boolean pointOfSale;

    private String address;

    @Column(name = "emission_point")
    private String emissionPoint;

    private String email;

    private String phone;

    // Agregando campo sitio web
    @Column(name = "website")
    private String website;

    @Column(name = "special_taxpayer")
    private String specialTaxpayer;

    @Column(name = "retention_agent")
    private String retentionAgent;

    // Agregando campo obligado a llevar contabilidad
    @Column(name = "accounting_obligated")
    private Boolean accountingObligated;

    // Agregando campo régimen microempresas
    @Column(name = "microenterprises_regime")
    private Boolean microenterprisesRegime;

    @Column(name = "rimpe_regime")
    private String rimpeRegime;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "environment", columnDefinition = "varchar(1)")
    private String environment = "1";

    // Agregando campo para enviar correos a destinatarios
    @Column(name = "send_emails")
    private Boolean sendEmails;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "digital_cert_p12", columnDefinition = "TEXT")
    private String digitalCertP12;

    @Column(name = "digital_cert_password")
    private String digitalCertPassword;

    @OneToMany(mappedBy = "invoiceIssuer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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

    public boolean getPointOfSale() {
        return pointOfSale;
    }
}