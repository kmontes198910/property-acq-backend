package com.kynsoft.invoiceservice.domain.dto;

import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuingSequence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para representar una secuencia de emisión de factura
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceIssuingSequenceDto {
    private UUID id;
    private String documentType;
    private Long currentSequential;
    private LocalDateTime lastUsedDate;
    private Boolean isActive;
    private UUID invoiceIssuerId;
    
    /**
     * Convierte una entidad InvoiceIssuingSequence a un DTO
     * 
     * @param sequence Entidad de secuencia de emisión
     * @return DTO con los datos de la secuencia
     */
    public static InvoiceIssuingSequenceDto fromEntity(InvoiceIssuingSequence sequence) {
        if (sequence == null) {
            return null;
        }
        
        return InvoiceIssuingSequenceDto.builder()
                .id(sequence.getId())
                .documentType(sequence.getDocumentType())
                .currentSequential(sequence.getCurrentSequential())
                .lastUsedDate(sequence.getLastUsedDate())
                .isActive(sequence.getIsActive())
                .invoiceIssuerId(sequence.getInvoiceIssuer() != null ? sequence.getInvoiceIssuer().getId() : null)
                .build();
    }
    
    /**
     * Convierte este DTO a una entidad InvoiceIssuingSequence
     * 
     * @param invoiceIssuer El emisor de facturas al que pertenece esta secuencia (opcional)
     * @return Entidad de secuencia de emisión
     */
    public InvoiceIssuingSequence toEntity(InvoiceIssuer invoiceIssuer) {
        return InvoiceIssuingSequence.builder()
                .id(this.id)
                .documentType(this.documentType)
                .currentSequential(this.currentSequential)
                .lastUsedDate(this.lastUsedDate)
                .isActive(this.isActive)
                .invoiceIssuer(invoiceIssuer)
                .build();
    }
}
