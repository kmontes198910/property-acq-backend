package com.kynsof.hospitalizationService.application.command.hospitalization.update;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateHospitalizationRequest {

    private UUID patient;
    private UUID emergencyCase;
    private UUID attendingDoctor;
    private String admissionDate;
    private String assignedRoom;
    private String hospitalizationStatus;
}
