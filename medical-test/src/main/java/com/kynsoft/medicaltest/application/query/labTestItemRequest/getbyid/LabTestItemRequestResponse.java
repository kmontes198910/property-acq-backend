package com.kynsoft.medicaltest.application.query.labTestItemRequest.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.medicaltest.domain.dto.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestItemRequestResponse implements IResponse, Serializable {

    private UUID id;
    private LabTestRequestDto order;
    private String code;
    private String examinationType;
    private String status;
    private LocalDate completionDate;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public LabTestItemRequestResponse(LabTestItemRequestDto dto) {
        this.id = dto.getId();
        this.order = dto.getOrder();
        this.code = dto.getCode();
        this.examinationType = dto.getExaminationType();
        this.status = dto.getStatus();
        this.completionDate = dto.getCompletionDate();
        this.observations = dto.getObservations();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }

}
