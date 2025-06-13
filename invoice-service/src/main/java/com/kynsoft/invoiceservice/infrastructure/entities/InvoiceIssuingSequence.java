package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "invoice_issuing_sequences", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"invoice_issuer_id", "document_type"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceIssuingSequence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "document_type")
    private String documentType;
    
    @Column(name = "current_sequential")
    private Long currentSequential;
    
    @Column(name = "last_used_date")
    private java.time.LocalDateTime lastUsedDate;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_issuer_id")
    private Issuer invoiceIssuer;
}