package com.kynsof.hospitalizationService.application.command.hospitalization.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateHospitalizationCommand implements ICommand {

    private UUID id;
    private UUID patient;
    private UUID emergencyCase;
    private UUID attendingDoctor;
    private String admissionDate;
    private String assignedRoom;
    private String hospitalizationStatus;

    public UpdateHospitalizationCommand(UUID id, UUID patient, UUID emergencyCase, UUID attendingDoctor, String admissionDate, String assignedRoom, String hospitalizationStatus) {
        this.id = id;
        this.patient = patient;
        this.emergencyCase = emergencyCase;
        this.attendingDoctor = attendingDoctor;
        this.admissionDate = admissionDate;
        this.assignedRoom = assignedRoom;
        this.hospitalizationStatus = hospitalizationStatus;
    }

    public static UpdateHospitalizationCommand fromRequest(UpdateHospitalizationRequest request, UUID id) {
        return new UpdateHospitalizationCommand(
                id,
                request.getPatient(),
                request.getEmergencyCase(),
                request.getAttendingDoctor(),
                request.getAdmissionDate(),
                request.getAssignedRoom(),
                request.getHospitalizationStatus()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateHospitalizationMessage(id);
    }
}
