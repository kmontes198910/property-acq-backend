package com.kynsoft.cirugia.application.query.diagnosis.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.Diagnosis;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class DiagnosisResponse implements IResponse {
    private final UUID id;
    private final String icdCode;
    private final String description;
    private final String type;
    private final UUID surgeryId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UUID createdBy;
    private final UUID updatedBy;
    
    public DiagnosisResponse(Diagnosis diagnosis) {
        this.id = diagnosis.getId();
        this.icdCode = diagnosis.getIcdCode();
        this.description = diagnosis.getDescription();
        this.type = diagnosis.getType();
        this.surgeryId = diagnosis.getSurgeryId();
        this.createdAt = diagnosis.getCreatedAt();
        this.updatedAt = diagnosis.getUpdatedAt();
        this.createdBy = diagnosis.getCreatedBy();
        this.updatedBy = diagnosis.getUpdatedBy();
    }
}