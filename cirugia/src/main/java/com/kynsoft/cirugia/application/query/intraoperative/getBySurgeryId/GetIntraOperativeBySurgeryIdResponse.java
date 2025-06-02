package com.kynsoft.cirugia.application.query.intraoperative.getBySurgeryId;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.IntraOperative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetIntraOperativeBySurgeryIdResponse implements IResponse, Serializable {
    private UUID id;
    private UUID surgeryId;
    private LocalDate date;            // Fecha
    private LocalTime startTime;       // H. Inicio
    private LocalTime endTime;         // H. Fin
    private String procedureType;      // Tipo de Procedimiento
    private String anesthesiaType;     // Tipo de Anestesia
    private String projectedProcedure; // Procedimiento Proyectado
    private String performedProcedure; // Procedimiento Realizado
    private String dieresis;           // Diéresis
    private String expositionExploration; // Exposición y Exploración
    private String surgicalFindings;   // Hallazgos Quirúrgicos
    private Integer bloodLoss;         // Pérdida Sanguínea total (ml)
    private Integer approximateBlood;  // Sangrado aproximado (ml)
    private Boolean prostheticMaterial; // Uso de Material Protésico
    private String surgicalProcedure;   // Procedimiento Quirúrgico
    private String description;        // Descripción
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;

    public GetIntraOperativeBySurgeryIdResponse(IntraOperative intraOperative) {
        this.id = intraOperative.getId();
        this.surgeryId = intraOperative.getSurgeryId();
        this.date = intraOperative.getDate();
        this.startTime = intraOperative.getStartTime();
        this.endTime = intraOperative.getEndTime();
        this.procedureType = intraOperative.getProcedureType();
        this.anesthesiaType = intraOperative.getAnesthesiaType();
        this.projectedProcedure = intraOperative.getProjectedProcedure();
        this.performedProcedure = intraOperative.getPerformedProcedure();
        this.dieresis = intraOperative.getDieresis();
        this.expositionExploration = intraOperative.getExpositionExploration();
        this.surgicalFindings = intraOperative.getSurgicalFindings();
        this.bloodLoss = intraOperative.getBloodLoss();
        this.approximateBlood = intraOperative.getApproximateBlood();
        this.prostheticMaterial = intraOperative.getProstheticMaterial();
        this.surgicalProcedure = intraOperative.getSurgicalProcedure();
        this.description = intraOperative.getDescription();
        this.createdAt = intraOperative.getCreatedAt();
        this.updatedAt = intraOperative.getUpdatedAt();
        this.createdBy = intraOperative.getCreatedBy();
        this.updatedBy = intraOperative.getUpdatedBy();
    }
}