package com.kynsoft.cirugia.application.command.surgery.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateSurgeryCommand implements ICommand {

    private UUID id;
    private UUID surgeryId;
    private UUID patientId;
    private UUID doctorId;
    private UUID specialtyId;
    private UUID recoveryBedEntityId;
    private UUID operatingRoomId;
    private String surgeryType;
    private LocalDate scheduledDate;
    private LocalTime startTime;
    private LocalTime endingTime;
    private Boolean requiresHospitalization;
    private UUID updatedBy;

    public UpdateSurgeryCommand(UUID surgeryId, UUID patientId, UUID doctorId, UUID specialtyId,
                               UUID recoveryBedEntityId, UUID operatingRoomId, String surgeryType,
                               LocalDate scheduledDate, LocalTime startTime, LocalTime endingTime,
                               Boolean requiresHospitalization, UUID updatedBy) {
        this.id = UUID.randomUUID();
        this.surgeryId = surgeryId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.specialtyId = specialtyId;
        this.recoveryBedEntityId = recoveryBedEntityId;
        this.operatingRoomId = operatingRoomId;
        this.surgeryType = surgeryType;
        this.scheduledDate = scheduledDate;
        this.startTime = startTime;
        this.endingTime = endingTime;
        this.requiresHospitalization = requiresHospitalization;
        this.updatedBy = updatedBy;
    }

    public static UpdateSurgeryCommand fromRequest(UpdateSurgeryRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        UpdateSurgeryCommand command = new UpdateSurgeryCommand();
        command.id = UUID.randomUUID();
        command.surgeryId = request.getSurgeryId();
        command.patientId = request.getPatientId();
        command.doctorId = request.getDoctorId();
        command.specialtyId = request.getSpecialtyId();
        command.recoveryBedEntityId = request.getRecoveryBedEntityId();
        command.operatingRoomId = request.getOperatingRoomId();
        command.surgeryType = request.getSurgeryType();
        command.scheduledDate = request.getScheduledDate();
        command.startTime = request.getStartTime();
        command.endingTime = request.getEndingTime();
        command.requiresHospitalization = request.getRequiresHospitalization();
        command.updatedBy = request.getUpdatedBy();
        
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateSurgeryMessage(id);
    }
}