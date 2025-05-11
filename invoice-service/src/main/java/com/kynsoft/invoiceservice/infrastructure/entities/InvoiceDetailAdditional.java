package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "invoice_detail_additional")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailAdditional {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, length = 1000)
    private String value;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_detail_id")
    private InvoiceDetail invoiceDetail;
}