package com.kynsof.hospitalizationService.application.command.medicalStaff.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateMedicalStaffCommand implements ICommand {

    private UUID id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String licenseNumber;

    public CreateMedicalStaffCommand(String firstName, String lastName, String specialty, String licenseNumber) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.licenseNumber = licenseNumber;
    }

    public static CreateMedicalStaffCommand fromRequest(CreateMedicalStaffRequest request) {
        return new CreateMedicalStaffCommand(
                request.getFirstName(),
                request.getLastName(),
                request.getSpecialty(),
                request.getLicenseNumber()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateMedicalStaffMessage(id);
    }
}
