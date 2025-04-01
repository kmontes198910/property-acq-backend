package com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateHospitalDischargeSummaryCommand implements ICommand {

    private UUID id;
    private UUID hospitalization;
    private String dischargeDate;
    private String finalDiagnosis;
    private String treatmentsPerformed;
    private String recommendations;

    public UpdateHospitalDischargeSummaryCommand(UUID id, UUID hospitalization, String dischargeDate, String finalDiagnosis, String treatmentsPerformed, String recommendations) {
        this.id = id;
        this.hospitalization = hospitalization;
        this.dischargeDate = dischargeDate;
        this.finalDiagnosis = finalDiagnosis;
        this.treatmentsPerformed = treatmentsPerformed;
        this.recommendations = recommendations;
    }

    public static UpdateHospitalDischargeSummaryCommand fromRequest(UpdateHospitalDischargeSummaryRequest request, UUID id) {
        return new UpdateHospitalDischargeSummaryCommand(
                id,
                request.getHospitalization(),
                request.getDischargeDate(),
                request.getFinalDiagnosis(),
                request.getTreatmentsPerformed(),
                request.getRecommendations()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateHospitalDischargeSummaryMessage(id);
    }
}
