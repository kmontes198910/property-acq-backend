package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.*;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HospitalDischargeSummaryResponse implements IResponse {
    private UUID id;
    private HospitalizationDto hospitalization;
    private LocalDate dischargeDate;
    private String finalDiagnosis;
    private String treatmentsPerformed;
    private String recommendations;

    public HospitalDischargeSummaryResponse(HospitalDischargeSummaryDto dto) {
        this.id = dto.getId();
        this.hospitalization = dto.getHospitalization();
        this.dischargeDate = dto.getDischargeDate();
        this.finalDiagnosis = dto.getFinalDiagnosis();
        this.treatmentsPerformed = dto.getTreatmentsPerformed();
        this.recommendations = dto.getRecommendations();
    }

}
