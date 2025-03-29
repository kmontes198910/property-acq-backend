package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.*;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmergencyDischargeResponse implements IResponse {
    private UUID id;
    private EmergencyCaseDto emergencyCase;
    private String condition;
    private Boolean hospitalizationRequired;
    private Boolean externalConsultationRequired;
    private Boolean emergencyObservationRequired;
    private Boolean referralRequired;
    private Boolean reverseReferral;
    private String observations;
    private Integer restDays;

    public EmergencyDischargeResponse(EmergencyDischargeDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase();
        this.condition = dto.getCondition();
        this.hospitalizationRequired = dto.getHospitalizationRequired();
        this.externalConsultationRequired = dto.getExternalConsultationRequired();
        this.emergencyObservationRequired = dto.getEmergencyObservationRequired();
        this.referralRequired = dto.getReferralRequired();
        this.reverseReferral = dto.getReverseReferral();
        this.observations = dto.getObservations();
        this.restDays = dto.getRestDays();
    }

}
