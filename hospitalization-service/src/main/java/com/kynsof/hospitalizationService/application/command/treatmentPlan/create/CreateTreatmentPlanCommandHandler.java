package com.kynsof.hospitalizationService.application.command.treatmentPlan.create;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.TreatmentPlanDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.ITreatmentPlanService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateTreatmentPlanCommandHandler implements ICommandHandler<CreateTreatmentPlanCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final ITreatmentPlanService treatmentPlanService;

    public CreateTreatmentPlanCommandHandler(IEmergencyCaseService emergencyCaseService,
                                             ITreatmentPlanService treatmentPlanService) {
        this.emergencyCaseService = emergencyCaseService;
        this.treatmentPlanService = treatmentPlanService;
    }

    @Override
    public void handle(CreateTreatmentPlanCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());
        treatmentPlanService.create(new TreatmentPlanDto(
                command.getId(), 
                emergencyCaseDto, 
                command.getMedicationName(), 
                command.getAdministrationRoute(), 
                command.getDosage(), 
                command.getFrequency(), 
                command.getDaysOfTreatment(),
                command.getCode()
        ));
    }
}
