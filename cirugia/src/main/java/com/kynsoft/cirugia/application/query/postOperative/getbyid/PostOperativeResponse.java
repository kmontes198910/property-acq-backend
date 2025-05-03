package com.kynsoft.cirugia.application.query.postoperative.getbyid;

import com.kynsoft.cirugia.domain.dto.PostOperative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOperativeResponse {
    private UUID id;
    private UUID surgeryId;
    private String treatmentSummary;
    private String dischargeInstructions;
    private String lifeStatus;
    private String dischargeCondition;
    private Integer stayDays;
    private Integer restDays;
    private String clinicalSummary;
    private String evolutionSummary;
    private String diagnosticFindings;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    
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