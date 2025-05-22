package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "invoice_detail_tax")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailTax {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String code;

    @Column(name = "percentage_code", nullable = false)
    private String percentageCode;

    @Column(nullable = false)
    private BigDecimal rate;

    @Column(name = "taxable_base", nullable = false)
    private BigDecimal taxableBase;

    @Column(nullable = false)
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_detail_id")
    private InvoiceDetail invoiceDetail;
}
