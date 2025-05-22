package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "document_number", nullable = false)
    private String documentNumber; // Formato: establecimiento-puntoEmision-secuencial (001-001-000000001)
    
    @Column(name = "sequential", nullable = false)
    private String sequential;
    
    @Column(name = "access_key", length = 49)
    private String accessKey;
    
    @Column(name = "emission_date", nullable = false)
    private LocalDateTime emissionDate;
    
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;
    
    @Column(name = "discount")
    private BigDecimal discount;
    
    @Column(name = "tax_amount")
    private BigDecimal taxAmount;
    
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;
    
    @Column(name = "remission_guide")
    private String remissionGuide;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
    
    // Relación con el emisor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issuer_id", nullable = false)
    private InvoiceIssuer issuer;
    
    // Relación con el cliente/comprador
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    // Relaciones con otras entidades
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InvoiceDetail> details = new ArrayList<>();
    
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InvoicePayment> payments = new ArrayList<>();
    
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InvoiceAdditionalField> additionalFields = new ArrayList<>();
    
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InvoiceTax> taxes = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper methods for bidirectional relationships
    public void addDetail(InvoiceDetail detail) {
        details.add(detail);
        detail.setInvoice(this);
    }
    
    public void removeDetail(InvoiceDetail detail) {
        details.remove(detail);
        detail.setInvoice(null);
    }
    
    public void addPayment(InvoicePayment payment) {
        payments.add(payment);
        payment.setInvoice(this);
    }
    
    public void removePayment(InvoicePayment payment) {
        payments.remove(payment);
        payment.setInvoice(null);
    }
    
    public void addAdditionalField(InvoiceAdditionalField field) {
        additionalFields.add(field);
        field.setInvoice(this);
    }
    
    public void removeAdditionalField(InvoiceAdditionalField field) {
        additionalFields.remove(field);
        field.setInvoice(null);
    }
    
    public void addTax(InvoiceTax tax) {
        taxes.add(tax);
        tax.setInvoice(this);
    }
    
    public void removeTax(InvoiceTax tax) {
        taxes.remove(tax);
        tax.setInvoice(null);
    }
}