package com.kynsoft.cirugia.application.query.evolution.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.Evolution;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class EvolutionResponse implements IResponse, Serializable {
    private final UUID id;
    private final UUID surgeryId;
    private final String therapeuticFluids;
    private final String prescriptionFluids;
    private final String generalCare;
    private final String monitoring;
    private final String diet;
    private final String analytics;
    private final String others;
    private final LocalDateTime evolutionDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UUID createdBy;
    private final UUID updatedBy;
    
    public EvolutionResponse(Evolution evolution) {
        this.id = evolution.getId();
        this.surgeryId = evolution.getSurgeryId();
        this.therapeuticFluids = evolution.getTherapeuticFluids();
        this.prescriptionFluids = evolution.getPrescriptionFluids();
        this.generalCare = evolution.getGeneralCare();
        this.monitoring = evolution.getMonitoring();
        this.diet = evolution.getDiet();
        this.analytics = evolution.getAnalytics();
        this.others = evolution.getOthers();
        this.evolutionDate = evolution.getEvolutionDate();
        this.createdAt = evolution.getCreatedAt();
        this.updatedAt = evolution.getUpdatedAt();
        this.createdBy = evolution.getCreatedBy();
        this.updatedBy = evolution.getUpdatedBy();
    }
}