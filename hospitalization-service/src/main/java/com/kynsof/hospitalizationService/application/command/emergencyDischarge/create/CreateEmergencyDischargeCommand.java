package com.kynsof.hospitalizationService.application.command.emergencyDischarge.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEmergencyDischargeCommand implements ICommand {

    private UUID id;
    private UUID emergencyCase;
    private String condition;
    private Boolean hospitalizationRequired;
    private Boolean externalConsultationRequired;
    private Boolean emergencyObservationRequired;
    private Boolean referralRequired;
    private Boolean reverseReferral;
    private String observations;
    private Integer restDays;

    public CreateEmergencyDischargeCommand(UUID emergencyCase, String condition, Boolean hospitalizationRequired, Boolean externalConsultationRequired, Boolean emergencyObservationRequired, Boolean referralRequired, Boolean reverseReferral, String observations, Integer restDays) {
        this.id = UUID.randomUUID();
        this.emergencyCase = emergencyCase;
        this.condition = condition;
        this.hospitalizationRequired = hospitalizationRequired;
        this.externalConsultationRequired = externalConsultationRequired;
        this.emergencyObservationRequired = emergencyObservationRequired;
        this.referralRequired = referralRequired;
        this.reverseReferral = reverseReferral;
        this.observations = observations;
        this.restDays = restDays;
    }

    public static CreateEmergencyDischargeCommand fromRequest(CreateEmergencyDischargeRequest request) {
        return new CreateEmergencyDischargeCommand(
                request.getEmergencyCase(),
                request.getCondition(),
                request.getHospitalizationRequired(),
                request.getExternalConsultationRequired(),
                request.getEmergencyObservationRequired(),
                request.getReferralRequired(),
                request.getReverseReferral(),
                request.getObservations(),
                request.getRestDays()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateEmergencyDischargeMessage(id);
    }
}
