package com.kynsof.hospitalizationService.application.command.treatmentPlan.update;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.TreatmentPlanDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.ITreatmentPlanService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateTreatmentPlanCommandHandler implements ICommandHandler<UpdateTreatmentPlanCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final ITreatmentPlanService treatmentPlanService;

    public UpdateTreatmentPlanCommandHandler(IEmergencyCaseService emergencyCaseService,
                                             ITreatmentPlanService treatmentPlanService) {
        this.emergencyCaseService = emergencyCaseService;
        this.treatmentPlanService = treatmentPlanService;
    }

    @Override
    public void handle(UpdateTreatmentPlanCommand command) {
        TreatmentPlanDto treatmentPlanDto = this.treatmentPlanService.findById(command.getId());
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());

        treatmentPlanDto.setEmergencyCase(emergencyCaseDto);
        treatmentPlanDto.setAdministrationRoute(command.getAdministrationRoute());
        treatmentPlanDto.setDaysOfTreatment(command.getDaysOfTreatment());
        treatmentPlanDto.setDosage(command.getDosage());
        treatmentPlanDto.setFrequency(command.getFrequency());
        treatmentPlanDto.setMedicationName(command.getMedicationName());

        treatmentPlanService.update(treatmentPlanDto);
    }
}
