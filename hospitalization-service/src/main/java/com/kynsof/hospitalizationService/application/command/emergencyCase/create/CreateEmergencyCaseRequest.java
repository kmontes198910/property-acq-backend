package com.kynsof.hospitalizationService.application.command.emergencyCase.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEmergencyCaseRequest {

    private UUID patient;
    private UUID bed;
    private String admissionDate;//formato: yyyy-MM-dd
    private String admissionTime;//formato: HH:mm
    private String admissionType;
    private String status;
}
