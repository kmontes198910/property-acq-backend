package com.kynsof.hospitalizationService.application.command.emergencyCase.update;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEmergencyCaseRequest {
    private UUID patient;
    private String admissionDate;//formato: yyyy-MM-dd
    private String admissionTime;//formato: HH:mm
    private String admissionType;
    private String status;
}
