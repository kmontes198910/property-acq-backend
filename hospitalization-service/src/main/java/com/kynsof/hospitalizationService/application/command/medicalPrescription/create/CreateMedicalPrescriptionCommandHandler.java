package com.kynsof.hospitalizationService.application.command.medicalPrescription.create;

import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;
import com.kynsof.hospitalizationService.domain.dto.MedicalPrescriptionDto;
import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.hospitalizationService.domain.service.IMedicalPrescriptionService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CreateMedicalPrescriptionCommandHandler implements ICommandHandler<CreateMedicalPrescriptionCommand> {

    private final IMedicalPrescriptionService medicalPrescriptionService;
    private final IHospitalizationService hospitalizationService;

    public CreateMedicalPrescriptionCommandHandler(IMedicalPrescriptionService medicalPrescriptionService,
                                               IHospitalizationService hospitalizationService) {
        this.medicalPrescriptionService = medicalPrescriptionService;
        this.hospitalizationService = hospitalizationService;
    }

    @Override
    public void handle(CreateMedicalPrescriptionCommand command) {
        HospitalizationDto hospitalizationDto = this.hospitalizationService.findById(command.getHospitalization());
        this.medicalPrescriptionService.create(new MedicalPrescriptionDto(command.getId(), hospitalizationDto, LocalDate.parse(command.getPrescriptionDate()), command.getInstructions()));
    }
}
