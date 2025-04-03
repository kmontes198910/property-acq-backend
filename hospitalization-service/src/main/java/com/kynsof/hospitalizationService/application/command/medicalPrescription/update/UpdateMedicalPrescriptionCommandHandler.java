package com.kynsof.hospitalizationService.application.command.medicalPrescription.update;

import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;
import com.kynsof.hospitalizationService.domain.dto.MedicalPrescriptionDto;
import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.hospitalizationService.domain.service.IMedicalPrescriptionService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class UpdateMedicalPrescriptionCommandHandler implements ICommandHandler<UpdateMedicalPrescriptionCommand> {

    private final IMedicalPrescriptionService medicalPrescriptionService;
    private final IHospitalizationService hospitalizationService;

    public UpdateMedicalPrescriptionCommandHandler(IMedicalPrescriptionService medicalPrescriptionService,
                                               IHospitalizationService hospitalizationService) {
        this.medicalPrescriptionService = medicalPrescriptionService;
        this.hospitalizationService = hospitalizationService;
    }

    @Override
    public void handle(UpdateMedicalPrescriptionCommand command) {
        HospitalizationDto hospitalizationDto = this.hospitalizationService.findById(command.getHospitalization());
        MedicalPrescriptionDto update = this.medicalPrescriptionService.findById(command.getId());

        update.setHospitalization(hospitalizationDto);
        update.setInstructions(command.getInstructions());
        update.setPrescriptionDate(LocalDate.parse(command.getPrescriptionDate()));

        this.medicalPrescriptionService.update(update);
    }
}
