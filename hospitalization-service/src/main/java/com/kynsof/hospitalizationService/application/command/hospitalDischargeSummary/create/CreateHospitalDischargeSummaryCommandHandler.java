package com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.create;

import com.kynsof.hospitalizationService.domain.dto.HospitalDischargeSummaryDto;
import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;
import com.kynsof.hospitalizationService.domain.service.IHospitalDischargeSummaryService;
import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CreateHospitalDischargeSummaryCommandHandler implements ICommandHandler<CreateHospitalDischargeSummaryCommand> {

    private final IHospitalizationService hospitalizationService;
    private final IHospitalDischargeSummaryService hospitalDischargeSummaryService;

    public CreateHospitalDischargeSummaryCommandHandler(IHospitalizationService hospitalizationService,
                                                        IHospitalDischargeSummaryService hospitalDischargeSummaryService) {
        this.hospitalizationService = hospitalizationService;
        this.hospitalDischargeSummaryService = hospitalDischargeSummaryService;
    }

    @Override
    public void handle(CreateHospitalDischargeSummaryCommand command) {
        HospitalizationDto hospitalizationDto = this.hospitalizationService.findById(command.getHospitalization());
        this.hospitalDischargeSummaryService.create(new HospitalDischargeSummaryDto(
                command.getId(), 
                hospitalizationDto, 
                LocalDate.parse(command.getDischargeDate()), 
                command.getFinalDiagnosis(), 
                command.getTreatmentsPerformed(), 
                command.getRecommendations()
        ));
    }
}
