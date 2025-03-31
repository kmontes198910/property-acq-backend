package com.kynsof.hospitalizationService.application.command.medicalStaff.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateMedicalStaffCommand implements ICommand {

    private UUID id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String licenseNumber;

    public UpdateMedicalStaffCommand(UUID id, String firstName, String lastName, String specialty, String licenseNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.licenseNumber = licenseNumber;
    }

    public static UpdateMedicalStaffCommand fromRequest(UpdateMedicalStaffRequest request, UUID id) {
        return new UpdateMedicalStaffCommand(
                id,
                request.getFirstName(),
                request.getLastName(),
                request.getSpecialty(),
                request.getLicenseNumber()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateMedicalStaffMessage(id);
    }
}
