package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceResponse implements IResponse{

    private UUID id;
    private String insuranceType;
    private String document;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LegalEntityBasicResponse legalEntity;
    private long daysSinceCreated;
    private long daysUntilSixty;

    public InsuranceResponse(InsuranceDto dto) {
        this.id = dto.getId();
        this.insuranceType = dto.getInsuranceType();
        this.document = dto.getDocument();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.legalEntity = dto.getLegalEntity() != null ? new LegalEntityBasicResponse(dto.getLegalEntity()) : null;
        this.daysSinceCreated = dto.getDaysSinceCreated();
        this.daysUntilSixty = dto.getDaysUntilSixty();
    }

}
