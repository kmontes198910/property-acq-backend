package com.kynsof.hospitalizationService.application.command.prescribedMedication.create;

import com.kynsof.hospitalizationService.domain.dto.MedicalPrescriptionDto;
import com.kynsof.hospitalizationService.domain.dto.PrescribedMedicationDto;
import com.kynsof.hospitalizationService.domain.service.IMedicalPrescriptionService;
import com.kynsof.hospitalizationService.domain.service.IPrescribedMedicationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreatePrescribedMedicationCommandHandler implements ICommandHandler<CreatePrescribedMedicationCommand> {

    private final IMedicalPrescriptionService medicalPrescriptionService;
    private final IPrescribedMedicationService prescribedMedicationService;

    public CreatePrescribedMedicationCommandHandler(IMedicalPrescriptionService medicalPrescriptionService,
                                               IPrescribedMedicationService prescribedMedicationService) {
        this.medicalPrescriptionService = medicalPrescriptionService;
        this.prescribedMedicationService = prescribedMedicationService;
    }

    @Override
    public void handle(CreatePrescribedMedicationCommand command) {
        MedicalPrescriptionDto medicalPrescriptionDto = this.medicalPrescriptionService.findById(command.getMedicalPrescription());
        this.prescribedMedicationService.create(new PrescribedMedicationDto(
                command.getId(), 
                medicalPrescriptionDto, 
                command.getMedicationName(), 
                command.getDosage(), 
                command.getFrequency(), 
                command.getAdministrationRoute(), 
                command.getDuration()
        ));
    }
}
