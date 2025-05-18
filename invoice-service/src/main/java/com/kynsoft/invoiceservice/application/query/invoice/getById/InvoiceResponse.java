package com.kynsoft.invoiceservice.application.query.invoice.getById;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.invoiceservice.domain.dto.InvoiceAdditionalFieldDto;
import com.kynsoft.invoiceservice.domain.dto.InvoiceDetailDto;
import com.kynsoft.invoiceservice.domain.dto.InvoicePaymentDto;
import com.kynsoft.invoiceservice.domain.dto.InvoiceTaxDto;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Respuesta con los datos de una factura
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse implements IResponse {
    private UUID id;
    private String documentNumber;
    private String sequential;
    private String accessKey;
    private String authorizationNumber;
    private LocalDateTime authorizationDate;
    private LocalDateTime issueDate;
    
    // Detalles financieros
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal taxAmount;
    private BigDecimal total;
    
    // Estado
    private InvoiceStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    
    // Información adicional
    private List<InvoiceDetailDto> details;
    private List<InvoiceTaxDto> taxes;
    private List<InvoicePaymentDto> payments;
    private List<InvoiceAdditionalFieldDto> additionalFields;
}
