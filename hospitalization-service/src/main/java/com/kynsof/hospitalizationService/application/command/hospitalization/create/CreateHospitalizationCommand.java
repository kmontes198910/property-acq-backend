package com.kynsof.hospitalizationService.application.command.hospitalization.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateHospitalizationCommand implements ICommand {

    private UUID id;
    private UUID patient;
    private UUID emergencyCase;
    private UUID attendingDoctor;
    private String admissionDate;
    private String assignedRoom;
    private String hospitalizationStatus;

    public CreateHospitalizationCommand(UUID patient, UUID emergencyCase, UUID attendingDoctor, String admissionDate, String assignedRoom, String hospitalizationStatus) {
        this.id = UUID.randomUUID();
        this.patient = patient;
        this.emergencyCase = emergencyCase;
        this.attendingDoctor = attendingDoctor;
        this.admissionDate = admissionDate;
        this.assignedRoom = assignedRoom;
        this.hospitalizationStatus = hospitalizationStatus;
    }

    public static CreateHospitalizationCommand fromRequest(CreateHospitalizationRequest request) {
        return new CreateHospitalizationCommand(
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
        return new CreateHospitalizationMessage(id);
    }
}
