package com.kynsof.hospitalizationService.application.command.medicalStaff.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMedicalStaffRequest {

    private String firstName;
    private String lastName;
    private String specialty;
    private String licenseNumber;
}
