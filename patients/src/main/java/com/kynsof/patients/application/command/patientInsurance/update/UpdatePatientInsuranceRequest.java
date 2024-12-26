package com.kynsof.patients.application.command.patientInsurance.update;

import com.kynsof.patients.domain.dto.enumTye.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePatientInsuranceRequest {
    private UUID insuranceId;
    private Status status;
}
