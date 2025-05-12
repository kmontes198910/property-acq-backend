package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "invoice_taxes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceTax {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "tax_code", nullable = false, length = 2)
    private String taxCode; // 2: IVA, 3: ICE, etc.
    
    @Column(name = "rate_code", nullable = false, length = 4)
    private String rateCode; // Código de porcentaje según SRI: 0, 2, 3, etc.
    
    @Column(name = "rate_percentage", nullable = false)
    private BigDecimal ratePercentage; // 12%, 14%, etc.
    
    @Column(name = "taxable_amount", nullable = false)
    private BigDecimal taxableAmount;
    
    @Column(name = "tax_amount", nullable = false)
    private BigDecimal taxAmount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}