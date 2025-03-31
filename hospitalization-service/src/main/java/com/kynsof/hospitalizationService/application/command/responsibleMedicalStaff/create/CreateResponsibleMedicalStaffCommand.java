package com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateResponsibleMedicalStaffCommand implements ICommand {

    private UUID id;
    private UUID emergencyCase;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private String dateOfRecord;
    private String signature;
    private String seal;

    public CreateResponsibleMedicalStaffCommand(UUID emergencyCase, String firstName, String lastName, String identificationNumber, String dateOfRecord, String signature, String seal) {
        this.id = UUID.randomUUID();
        this.emergencyCase = emergencyCase;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identificationNumber = identificationNumber;
        this.dateOfRecord = dateOfRecord;
        this.signature = signature;
        this.seal = seal;
    }

    public static CreateResponsibleMedicalStaffCommand fromRequest(CreateResponsibleMedicalStaffRequest request) {
        return new CreateResponsibleMedicalStaffCommand(
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
        return new CreateResponsibleMedicalStaffMessage(id);
    }
}
