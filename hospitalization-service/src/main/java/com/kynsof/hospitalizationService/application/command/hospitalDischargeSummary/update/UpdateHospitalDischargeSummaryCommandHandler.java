package com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.update;

import com.kynsof.hospitalizationService.domain.dto.HospitalDischargeSummaryDto;
import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;
import com.kynsof.hospitalizationService.domain.service.IHospitalDischargeSummaryService;
import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class UpdateHospitalDischargeSummaryCommandHandler implements ICommandHandler<UpdateHospitalDischargeSummaryCommand> {

    private final IHospitalizationService hospitalizationService;
    private final IHospitalDischargeSummaryService hospitalDischargeSummaryService;

    public UpdateHospitalDischargeSummaryCommandHandler(IHospitalizationService hospitalizationService,
                                                        IHospitalDischargeSummaryService hospitalDischargeSummaryService) {
        this.hospitalizationService = hospitalizationService;
        this.hospitalDischargeSummaryService = hospitalDischargeSummaryService;
    }

    @Override
    public void handle(UpdateHospitalDischargeSummaryCommand command) {
        HospitalizationDto hospitalizationDto = this.hospitalizationService.findById(command.getHospitalization());
        HospitalDischargeSummaryDto update = this.hospitalDischargeSummaryService.findById(command.getId());

        update.setDischargeDate(LocalDate.parse(command.getDischargeDate()));
        update.setFinalDiagnosis(command.getFinalDiagnosis());
        update.setHospitalization(hospitalizationDto);
        update.setRecommendations(command.getRecommendations());
        update.setTreatmentsPerformed(command.getTreatmentsPerformed());

        this.hospitalDischargeSummaryService.update(update);
    }
}
