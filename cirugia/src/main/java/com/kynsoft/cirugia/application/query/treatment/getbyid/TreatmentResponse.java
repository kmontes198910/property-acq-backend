package com.kynsoft.cirugia.application.query.treatment.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.Treatment;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class TreatmentResponse implements IResponse, Serializable {
    private final UUID id;
    private final UUID surgeryId;
    private final String code;
    private final String name;
    private final String description;
    private final int quantity;
    private final String medicineUnit;
    private final String status;
    private final String process;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UUID createdBy;
    private final UUID updatedBy;
    
    public TreatmentResponse(Treatment treatment) {
        this.id = treatment.getId();
        this.surgeryId = treatment.getSurgeryId();
        this.code = treatment.getCode();
        this.name = treatment.getName();
        this.description = treatment.getDescription();
        this.quantity = treatment.getQuantity();
        this.medicineUnit = treatment.getMedicineUnit();
        this.status = treatment.getStatus();
        this.process = treatment.getProcess();
        this.createdAt = treatment.getCreatedAt();
        this.updatedAt = treatment.getUpdatedAt();
        this.createdBy = treatment.getCreatedBy();
        this.updatedBy = treatment.getUpdatedBy();
    }
}