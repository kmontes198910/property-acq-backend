package com.kynsof.hospitalizationService.application.command.medicalEvolution.create;

import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;
import com.kynsof.hospitalizationService.domain.dto.MedicalEvolutionDto;
import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.hospitalizationService.domain.service.IMedicalEvolutionService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CreateMedicalEvolutionCommandHandler implements ICommandHandler<CreateMedicalEvolutionCommand> {

    private final IMedicalEvolutionService medicalEvolutionService;
    private final IHospitalizationService hospitalizationService;

    public CreateMedicalEvolutionCommandHandler(IMedicalEvolutionService medicalEvolutionService,
                                                IHospitalizationService hospitalizationService) {
        this.medicalEvolutionService = medicalEvolutionService;
        this.hospitalizationService = hospitalizationService;
    }

    @Override
    public void handle(CreateMedicalEvolutionCommand command) {
        HospitalizationDto hospitalizationDto = this.hospitalizationService.findById(command.getHospitalization());
        this.medicalEvolutionService.create(new MedicalEvolutionDto(
                command.getId(), 
                hospitalizationDto, 
                LocalDate.parse(command.getRecordDate()), 
                command.getObservations(), 
                command.getDiagnosis()
        ));
    }
}
