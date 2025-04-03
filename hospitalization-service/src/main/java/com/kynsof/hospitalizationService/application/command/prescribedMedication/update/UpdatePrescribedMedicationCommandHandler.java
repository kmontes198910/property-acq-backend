package com.kynsof.hospitalizationService.application.command.prescribedMedication.update;

import com.kynsof.hospitalizationService.domain.dto.MedicalPrescriptionDto;
import com.kynsof.hospitalizationService.domain.dto.PrescribedMedicationDto;
import com.kynsof.hospitalizationService.domain.service.IMedicalPrescriptionService;
import com.kynsof.hospitalizationService.domain.service.IPrescribedMedicationService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdatePrescribedMedicationCommandHandler implements ICommandHandler<UpdatePrescribedMedicationCommand> {

    private final IMedicalPrescriptionService medicalPrescriptionService;
    private final IPrescribedMedicationService prescribedMedicationService;

    public UpdatePrescribedMedicationCommandHandler(IMedicalPrescriptionService medicalPrescriptionService,
                                               IPrescribedMedicationService prescribedMedicationService) {
        this.medicalPrescriptionService = medicalPrescriptionService;
        this.prescribedMedicationService = prescribedMedicationService;
    }

    @Override
    public void handle(UpdatePrescribedMedicationCommand command) {
        MedicalPrescriptionDto medicalPrescriptionDto = this.medicalPrescriptionService.findById(command.getMedicalPrescription());
        PrescribedMedicationDto update = this.prescribedMedicationService.findById(command.getId());
        update.setAdministrationRoute(command.getAdministrationRoute());
        update.setDosage(command.getDosage());
        update.setDuration(command.getDuration());
        update.setFrequency(command.getFrequency());
        update.setMedicalPrescription(medicalPrescriptionDto);
        update.setMedicationName(command.getMedicationName());

        this.prescribedMedicationService.update(update);
    }
}
