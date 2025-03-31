package com.kynsof.hospitalizationService.application.command.medicalEvolution.update;

import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;
import com.kynsof.hospitalizationService.domain.dto.MedicalEvolutionDto;
import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.hospitalizationService.domain.service.IMedicalEvolutionService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class UpdateMedicalEvolutionCommandHandler implements ICommandHandler<UpdateMedicalEvolutionCommand> {

    private final IMedicalEvolutionService medicalEvolutionService;
    private final IHospitalizationService hospitalizationService;

    public UpdateMedicalEvolutionCommandHandler(IMedicalEvolutionService medicalEvolutionService,
                                                IHospitalizationService hospitalizationService) {
        this.medicalEvolutionService = medicalEvolutionService;
        this.hospitalizationService = hospitalizationService;
    }

    @Override
    public void handle(UpdateMedicalEvolutionCommand command) {
        HospitalizationDto hospitalizationDto = this.hospitalizationService.findById(command.getHospitalization());
        MedicalEvolutionDto medicalEvolutionDto = this.medicalEvolutionService.findById(command.getId());

        medicalEvolutionDto.setDiagnosis(command.getDiagnosis());
        medicalEvolutionDto.setHospitalization(hospitalizationDto);
        medicalEvolutionDto.setObservations(command.getObservations());
        medicalEvolutionDto.setRecordDate(LocalDate.parse(command.getRecordDate()));

        this.medicalEvolutionService.update(medicalEvolutionDto);
    }
}
