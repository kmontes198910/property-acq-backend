package com.kynsof.hospitalizationService.application.command.emergencyCase.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEmergencyCaseCommand implements ICommand {

    private UUID id;
    private UUID patient;
    private String admissionDate;//formato: yyyy-MM-dd
    private String admissionTime;//formato: HH:mm
    private String admissionType;
    private String status;
    private UUID bed;

    public CreateEmergencyCaseCommand(UUID patient, String admissionDate, String admissionTime, String admissionType, String status, UUID bed) {
        this.id = UUID.randomUUID();
        this.patient = patient;
        this.admissionDate = admissionDate;
        this.admissionTime = admissionTime;
        this.admissionType = admissionType;
        this.status = status;
        this.bed = bed;
    }

    public static CreateEmergencyCaseCommand fromRequest(CreateEmergencyCaseRequest request) {
        return new CreateEmergencyCaseCommand(
                request.getPatient(),
                request.getAdmissionDate(),
                request.getAdmissionTime(),
                request.getAdmissionType(),
                request.getStatus(),
                request.getBed()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateEmergencyCaseMessage(id);
    }
}
