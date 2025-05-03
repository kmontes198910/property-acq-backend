package com.kynsoft.cirugia.application.command.surgery.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
public class CreateSurgeryCommand implements ICommand {
    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private UUID specialtyId;
    private UUID recoveryBedEntityId;
    private UUID businessId;
    private String surgeryType;
    private Boolean requiresHospitalization;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endingTime;
    private UUID createdBy;

    public CreateSurgeryCommand(UUID patientId, UUID doctorId, UUID specialtyId, UUID recoveryBedEntityId, 
                               UUID businessId, String surgeryType,
                               Boolean requiresHospitalization, LocalDate scheduledDate, 
                               LocalTime startTime, LocalTime endingTime, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.specialtyId = specialtyId;
        this.recoveryBedEntityId = recoveryBedEntityId;
        this.businessId = businessId;
        this.surgeryType = surgeryType;
        this.requiresHospitalization = requiresHospitalization;
        this.scheduledDate = scheduledDate;
        this.startTime = startTime;
        this.endingTime = endingTime;
        this.createdBy = createdBy;
    }

    public static CreateSurgeryCommand fromRequest(CreateSurgeryRequest request) {
        return new CreateSurgeryCommand(
                request.getPatientId(),
                request.getDoctorId(),
                request.getSpecialtyId(),
                request.getRecoveryBedEntityId(),
                request.getBusinessId(),
                request.getSurgeryType(),
                request.getRequiresHospitalization(),
                request.getScheduledDate(),
                request.getStartTime(),
                request.getEndingTime(),
                UUID.randomUUID() // createdBy, como no existe en el request se genera un UUID
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateSurgeryMessage(id);
    }
}