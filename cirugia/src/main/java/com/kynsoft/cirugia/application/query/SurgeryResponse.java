package com.kynsoft.cirugia.application.query;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.application.response.DoctorResponse;
import com.kynsoft.cirugia.application.response.PatientResponse;
import com.kynsoft.cirugia.application.response.SpecialtyResponse;
import com.kynsoft.cirugia.infrastructure.entities.SurgeryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Setter
@Slf4j
public class SurgeryResponse implements IResponse, Serializable {
    private UUID id;
    private PatientResponse patient;
    private DoctorResponse doctor;
    private SpecialtyResponse specialty;
    private UUID recoveryBedEntityId;
    private UUID operatingRoomId;
    private String surgeryType;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endingTime;
    private Boolean requiresHospitalization;
    private String status;  // SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private UUID businessId;

    // Constructor adicional para mapear directamente desde la entidad con relaciones cargadas
    public SurgeryResponse(SurgeryEntity entity) {
        this.id = entity.getId();
        this.recoveryBedEntityId = entity.getRecoveryBedEntityId();
        this.operatingRoomId = entity.getOperatingRoomId();
        this.surgeryType = entity.getSurgeryType();
        this.scheduledDate = entity.getScheduledDate();
        this.startTime = entity.getStartTime();
        this.endingTime = entity.getEndingTime();
        this.requiresHospitalization = entity.getRequiresHospitalization();
        this.status = entity.getStatus();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.createdBy = entity.getCreatedBy();
        this.updatedBy = entity.getUpdatedBy();
        this.businessId = entity.getBusinessId();

        if (entity.getPatient() != null) {
            this.patient = new PatientResponse(entity.getPatient());
        }

        if (entity.getDoctor() != null) {
            this.doctor = new DoctorResponse(entity.getDoctor());
        }

        if (entity.getSpecialty() != null) {
            this.specialty = new SpecialtyResponse(entity.getSpecialty());
        }

    }
}