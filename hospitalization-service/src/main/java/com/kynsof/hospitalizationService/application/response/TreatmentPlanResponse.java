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
public class TreatmentPlanResponse implements IResponse {
    private UUID id;
    private EmergencyCaseDto emergencyCase;
    private String medicationName;
    private String administrationRoute;
    private String dosage;
    private String frequency;
    private Integer daysOfTreatment;

    public TreatmentPlanResponse(TreatmentPlanDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase();
        this.medicationName = dto.getMedicationName();
        this.administrationRoute = dto.getAdministrationRoute();
        this.dosage = dto.getDosage();
        this.frequency = dto.getFrequency();
        this.daysOfTreatment = dto.getDaysOfTreatment();
    }

}
