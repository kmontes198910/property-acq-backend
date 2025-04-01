package com.kynsof.hospitalizationService.application.command.medicalPrescription.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateMedicalPrescriptionCommand implements ICommand {

    private UUID id;
    private UUID hospitalization;
    private String prescriptionDate;
    private String instructions;

    public CreateMedicalPrescriptionCommand(UUID hospitalization, String prescriptionDate, String instructions) {
        this.id = UUID.randomUUID();
        this.hospitalization = hospitalization;
        this.prescriptionDate = prescriptionDate;
        this.instructions = instructions;
    }

    public static CreateMedicalPrescriptionCommand fromRequest(CreateMedicalPrescriptionRequest request) {
        return new CreateMedicalPrescriptionCommand(
                request.getHospitalization(),
                request.getPrescriptionDate(),
                request.getInstructions()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateMedicalPrescriptionMessage(id);
    }
}
