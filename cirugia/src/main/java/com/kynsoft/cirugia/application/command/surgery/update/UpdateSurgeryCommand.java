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


    public static UpdateSurgeryCommand fromRequest(UpdateSurgeryRequest request, UUID updatedBy) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        UpdateSurgeryCommand command = new UpdateSurgeryCommand();
        command.id = UUID.randomUUID();
        command.surgeryId = request.getSurgeryId();
        command.patientId = request.getPatientId();
        command.doctorId = request.getDoctorId();
        command.specialtyId = request.getSpecialtyId();
        command.surgeryType = request.getSurgeryType();
        command.scheduledDate = request.getScheduledDate();
        command.startTime = request.getStartTime();
        command.endingTime = request.getEndingTime();
        command.requiresHospitalization = request.getRequiresHospitalization();
        command.updatedBy = updatedBy;
        
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateSurgeryMessage(id);
    }
}