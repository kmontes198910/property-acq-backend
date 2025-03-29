package com.kynsof.hospitalizationService.application.command.emergencyDischarge.create;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEmergencyDischargeRequest {

    private UUID emergencyCase;
    private String condition;
    private Boolean hospitalizationRequired;
    private Boolean externalConsultationRequired;
    private Boolean emergencyObservationRequired;
    private Boolean referralRequired;
    private Boolean reverseReferral;
    private String observations;
    private Integer restDays;
}
