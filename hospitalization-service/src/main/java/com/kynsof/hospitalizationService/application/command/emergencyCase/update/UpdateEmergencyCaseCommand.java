package com.kynsof.hospitalizationService.application.command.emergencyCase.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateEmergencyCaseCommand implements ICommand {

    private UUID id;
    private UUID patient;
    private String admissionDate;//formato: yyyy-MM-dd
    private String admissionTime;//formato: HH:mm
    private String admissionType;
    private String status;

    public UpdateEmergencyCaseCommand(UUID id, UUID patient, String admissionDate, String admissionTime, String admissionType, String status) {
        this.id = id;
        this.patient = patient;
        this.admissionDate = admissionDate;
        this.admissionTime = admissionTime;
        this.admissionType = admissionType;
        this.status = status;
    }

    public static UpdateEmergencyCaseCommand fromRequest(UpdateEmergencyCaseRequest request, UUID id) {
        return new UpdateEmergencyCaseCommand(
                id,
                request.getPatient(),
                request.getAdmissionDate(),
                request.getAdmissionTime(),
                request.getAdmissionType(),
                request.getStatus()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateEmergencyCaseMessage(id);
    }
}
