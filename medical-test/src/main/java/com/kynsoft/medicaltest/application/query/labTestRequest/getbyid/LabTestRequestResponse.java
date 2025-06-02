package com.kynsoft.medicaltest.application.query.labTestRequest.getbyid;

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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LabTestRequestResponse implements IResponse, Serializable {

    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private LocalDate creationDate;
    private String status;
    private String observations;
    private UUID businessId;
    private List<LabTestItemResponse> examinations = new ArrayList<>();
    private UUID createdBy;
    private UUID updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
    private PatientDto patient;

    public LabTestRequestResponse(LabTestRequestDto dto) {
        this.id = dto.getId();
        this.patientId = dto.getPatientId();
        this.doctorId = dto.getDoctorId();
        this.creationDate = dto.getCreationDate();
        this.status = dto.getStatus();
        this.observations = dto.getObservations();
        this.businessId = dto.getBusinessId();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.isActive = dto.isActive();
        this.patient = dto.getPatient();
        if (dto.getExaminations() != null) {
            for (LabTestItemRequestDto examination : dto.getExaminations()) {
                examinations.add(LabTestItemResponse.builder()
                        .code(examination.getCode())
                        .completionDate(examination.getCompletionDate())
                        .examinationType(examination.getExaminationType())
                        .id(examination.getId())
                        .observations(examination.getObservations())
                        .status(examination.getStatus())
                        .build()
                );
            }
        }
    }

}
