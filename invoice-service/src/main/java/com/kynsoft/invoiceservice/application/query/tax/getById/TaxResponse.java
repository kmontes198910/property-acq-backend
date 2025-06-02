package com.kynsoft.invoiceservice.application.query.tax.getById;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.invoiceservice.domain.dto.TaxDto;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax.TaxType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Respuesta para la consulta de un impuesto por ID
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxResponse implements IResponse {
    
    private UUID id;
    private String code;
    private String name;
    private String description;
    private BigDecimal value;
    private TaxType taxType;
    private Boolean status;
    private Boolean isPredetermined;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    
    public static TaxResponse fromDto(TaxDto dto) {
        return TaxResponse.builder()
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
