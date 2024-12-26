package com.kynsof.patients.application.command.patientInsurance.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePatientInsuranceRequest {

    private String patientId;
    private UUID insuranceId;
}
