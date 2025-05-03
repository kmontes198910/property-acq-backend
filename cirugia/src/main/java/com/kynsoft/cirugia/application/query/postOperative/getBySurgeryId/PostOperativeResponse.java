package com.kynsoft.cirugia.application.query.postOperative.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.PostOperative;
import lombok.Getter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PostOperativeResponse implements IResponse, Serializable {
    private final UUID id;
    private final UUID surgeryId;
    private final String treatmentSummary;
    private final String dischargeInstructions;
    private final String lifeStatus;
    private final String dischargeCondition;
    private final Integer stayDays;
    private final Integer restDays;
    private final String clinicalSummary;
    private final String evolutionSummary;
    private final String diagnosticFindings;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UUID createdBy;
    private final UUID updatedBy;

    public PostOperativeResponse(PostOperative postOperative) {
        this.id = postOperative.getId();
        this.surgeryId = postOperative.getSurgeryId();
        this.treatmentSummary = postOperative.getTreatmentSummary();
        this.dischargeInstructions = postOperative.getDischargeInstructions();
        this.lifeStatus = postOperative.getLifeStatus();
        this.dischargeCondition = postOperative.getDischargeCondition();
        this.stayDays = postOperative.getStayDays();
        this.restDays = postOperative.getRestDays();
        this.clinicalSummary = postOperative.getClinicalSummary();
        this.evolutionSummary = postOperative.getEvolutionSummary();
        this.diagnosticFindings = postOperative.getDiagnosticFindings();
        this.createdAt = postOperative.getCreatedAt();
        this.updatedAt = postOperative.getUpdatedAt();
        this.createdBy = postOperative.getCreatedBy();
        this.updatedBy = postOperative.getUpdatedBy();
    }
}
