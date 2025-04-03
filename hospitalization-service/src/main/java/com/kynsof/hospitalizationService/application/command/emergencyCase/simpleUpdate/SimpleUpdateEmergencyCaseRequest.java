package com.kynsof.hospitalizationService.application.command.emergencyCase.simpleUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleUpdateEmergencyCaseRequest {
    private String admissionDate;//formato: yyyy-MM-dd
    private String admissionTime;//formato: HH:mm
    private String admissionType;
    private String status;
}
