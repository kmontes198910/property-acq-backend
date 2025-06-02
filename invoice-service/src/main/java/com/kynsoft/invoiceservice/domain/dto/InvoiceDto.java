package com.kynsoft.invoiceservice.domain.dto;

import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.dto.InvoiceIssuerDTO;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceDto {
    private UUID id;
    private String documentNumber;
    private String sequential;
    private String accessKey;
    private LocalDateTime emissionDate;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private BigDecimal tip;
    private InvoiceStatus status;
    private String remissionGuide;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    
    // Relaciones
    private InvoiceIssuerDTO issuer;
    private UUID issuerId;
    private CustomerDto customer;
    
    @Builder.Default
    private List<InvoiceDetailDto> details = new ArrayList<>();
    
    @Builder.Default
    private List<InvoicePaymentDto> payments = new ArrayList<>();
    
    @Builder.Default
    private List<InvoiceAdditionalFieldDto> additionalFields = new ArrayList<>();
    
    @Builder.Default
    private List<InvoiceTaxDto> taxes = new ArrayList<>();
}
