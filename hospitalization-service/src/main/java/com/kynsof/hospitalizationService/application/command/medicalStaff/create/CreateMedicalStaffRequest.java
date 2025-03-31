package com.kynsof.hospitalizationService.application.command.medicalStaff.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMedicalStaffRequest {

    private String firstName;
    private String lastName;
    private String specialty;
    private String licenseNumber;
}
