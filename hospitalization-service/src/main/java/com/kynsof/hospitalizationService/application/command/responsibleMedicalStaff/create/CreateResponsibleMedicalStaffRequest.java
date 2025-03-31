package com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateResponsibleMedicalStaffRequest {

    private UUID emergencyCase;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private String dateOfRecord;
    private String signature;
    private String seal;
}
