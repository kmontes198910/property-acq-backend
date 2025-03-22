package com.kynsof.patients.application.command.additionalInfo.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAdditionalInfoCommand implements ICommand {
    private UUID id;
    private UUID patientId;
    private String maritalStatus;
    private String occupation;
    private String emergencyContactName;
    private String emergencyContactPhone;

    public CreateAdditionalInfoCommand(UUID patientId, String maritalStatus, String occupation,
                                       String emergencyContactName, String emergencyContactPhone) {
        this.patientId = patientId;
        this.maritalStatus = maritalStatus;
        this.occupation = occupation;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public static CreateAdditionalInfoCommand fromRequest(CreateAdditionalInfoRequest request) {
        return new CreateAdditionalInfoCommand(request.getPatientId(), request.getMaritalStatus(),
                request.getOccupation(), request.getEmergencyContactName(),request.getEmergencyContactPhone());
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreatAdditionalInfoMessage(id);
    }
}
