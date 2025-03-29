package com.kynsof.hospitalizationService.application.command.emergencyDischarge.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateEmergencyDischargeCommand implements ICommand {

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

    public UpdateEmergencyDischargeCommand(UUID id, UUID emergencyCase, String condition, Boolean hospitalizationRequired, Boolean externalConsultationRequired, Boolean emergencyObservationRequired, Boolean referralRequired, Boolean reverseReferral, String observations, Integer restDays) {
        this.id = id;
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

    public static UpdateEmergencyDischargeCommand fromRequest(UpdateEmergencyDischargeRequest request, UUID id) {
        return new UpdateEmergencyDischargeCommand(
                id,
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
        return new UpdateEmergencyDischargeMessage(id);
    }
}
