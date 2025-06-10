package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "invoice_details")
@Getter
@Setter
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
    private Set<InvoiceDetailAdditional> additionalInfo = new LinkedHashSet<>();
    
    @OneToMany(mappedBy = "invoiceDetail", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private Set<InvoiceDetailTax> taxes = new LinkedHashSet<>();

    // Helper method
    public void addAdditionalInfo(InvoiceDetailAdditional additional) {
        additionalInfo.add(additional);
        additional.setInvoiceDetail(this);
    }
    
    public void removeAdditionalInfo(InvoiceDetailAdditional additional) {
        additionalInfo.remove(additional);
        additional.setInvoiceDetail(null);
    }

    // Helper methods for Tax
    public void addTax(InvoiceDetailTax tax) {
        taxes.add(tax);
        tax.setInvoiceDetail(this);
    }

    public void removeTax(InvoiceDetailTax tax) {
        taxes.remove(tax);
        tax.setInvoiceDetail(null);
    }
}
