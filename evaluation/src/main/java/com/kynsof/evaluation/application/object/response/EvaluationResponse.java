package com.kynsof.evaluation.application.object.response;

import com.kynsof.evaluation.domain.dto.*;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationResponse implements IResponse {
    private UUID id;
    private PatientResponse patient;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EvaluationResponse(EvaluationDto dto) {
        this.id = dto.getId();
        this.patient = dto.getPatient() != null ? new PatientResponse(dto.getPatient()) : null;
        this.consultationReason = dto.getConsultationReason();
        this.medicalHistory = dto.getMedicalHistory();
        this.physicalExam = dto.getPhysicalExam();
        this.observation = dto.getObservation();
    }

}