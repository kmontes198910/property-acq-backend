package com.kynsoft.invoiceservice.domain.dto;

import com.kynsoft.invoiceservice.infrastructure.entities.Tax;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para la transferencia de datos de impuestos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxDto {
    
    private UUID id;
    private String code;
    private String name;
    private String description;
    private BigDecimal value;
    private Tax.TaxType taxType;
    private Boolean status;
    private Boolean isPredetermined;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    
    /**
     * Convierte una entidad Tax a un DTO
     * 
     * @param tax Entidad de impuesto
     * @return DTO de impuesto
     */
    public static TaxDto fromEntity(Tax tax) {
        if (tax == null) return null;
        
        return TaxDto.builder()
                .id(tax.getId())
                .code(tax.getCode())
                .name(tax.getName())
                .description(tax.getDescription())
                .value(tax.getValue())
                .taxType(tax.getTaxType())
                .status(tax.getStatus())
                .isPredetermined(tax.getIsPredetermined())
                .createdAt(tax.getCreatedAt())
                .updatedAt(tax.getUpdatedAt())
                .createdBy(tax.getCreatedBy())
                .updatedBy(tax.getUpdatedBy())
                .build();
    }
    
    /**
     * Convierte un DTO a una entidad Tax
     * 
     * @param dto DTO de impuesto
     * @return Entidad de impuesto
     */
    public static Tax toEntity(TaxDto dto) {
        if (dto == null) return null;
        
        return Tax.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .name(dto.getName())
                .description(dto.getDescription())
                .value(dto.getValue())
                .taxType(dto.getTaxType())
                .status(dto.getStatus())
                .isPredetermined(dto.getIsPredetermined())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .createdBy(dto.getCreatedBy())
                .updatedBy(dto.getUpdatedBy())
                .build();
    }
}
