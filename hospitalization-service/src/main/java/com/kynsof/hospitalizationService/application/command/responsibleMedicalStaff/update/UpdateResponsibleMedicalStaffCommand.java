package com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateResponsibleMedicalStaffCommand implements ICommand {

    private UUID id;
    private UUID emergencyCase;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private String dateOfRecord;
    private String signature;
    private String seal;

    public UpdateResponsibleMedicalStaffCommand(UUID id, UUID emergencyCase, String firstName, String lastName, String identificationNumber, String dateOfRecord, String signature, String seal) {
        this.id = id;
        this.emergencyCase = emergencyCase;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identificationNumber = identificationNumber;
        this.dateOfRecord = dateOfRecord;
        this.signature = signature;
        this.seal = seal;
    }

    public static UpdateResponsibleMedicalStaffCommand fromRequest(UpdateResponsibleMedicalStaffRequest request, UUID id) {
        return new UpdateResponsibleMedicalStaffCommand(
                id,
                request.getEmergencyCase(),
                request.getFirstName(),
                request.getLastName(),
                request.getIdentificationNumber(),
                request.getDateOfRecord(),
                request.getSignature(),
                request.getSeal()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateResponsibleMedicalStaffMessage(id);
    }
}
