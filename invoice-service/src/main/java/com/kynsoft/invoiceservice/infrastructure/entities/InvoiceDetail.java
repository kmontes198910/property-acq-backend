package com.kynsoft.invoiceservice.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoice_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "line_number")
    private Integer lineNumber;
    
    @Column(name = "main_code")
    private String mainCode;
    
    @Column(name = "auxiliary_code")
    private String auxiliaryCode;
    
    @Column(nullable = false, length = 500)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal quantity;
    
    @Column(name = "unit_of_measure")
    private String unitOfMeasure;
    
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
    
    @Column(name = "discount")
    private BigDecimal discount;
    
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subtotal;
    
    // Información de impuestos a nivel del detalle
    @Column(name = "iva_code")
    private String ivaCode;
    
    @Column(name = "iva_rate")
    private BigDecimal ivaRate;
    
    @Column(name = "iva_amount")
    private BigDecimal ivaAmount;
    
    @Column(name = "ice_code")
    private String iceCode;
    
    @Column(name = "ice_rate")
    private BigDecimal iceRate;
    
    @Column(name = "ice_amount")
    private BigDecimal iceAmount;
    
    @Column(name = "total_with_tax", nullable = false)
    private BigDecimal totalWithTax;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @OneToMany(mappedBy = "invoiceDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<InvoiceDetailAdditional> additionalInfo = new ArrayList<>();
    
    // Helper method
    public void addAdditionalInfo(InvoiceDetailAdditional additional) {
        additionalInfo.add(additional);
        additional.setInvoiceDetail(this);
    }
    
    public void removeAdditionalInfo(InvoiceDetailAdditional additional) {
        additionalInfo.remove(additional);
        additional.setInvoiceDetail(null);
    }
}