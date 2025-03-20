package com.kynsof.evaluation.application.object.response;

import com.kynsof.evaluation.application.query.business.getById.BusinessResponse;
import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationResponse implements IResponse {
    private UUID id;
    private PatientResponse patient;
    private BusinessResponse business;
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
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.business = dto.getBusiness() != null ? new BusinessResponse(dto.getBusiness()) : null;
    }

}