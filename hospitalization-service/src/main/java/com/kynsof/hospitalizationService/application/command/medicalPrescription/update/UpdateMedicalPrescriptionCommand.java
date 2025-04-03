package com.kynsof.hospitalizationService.application.command.medicalPrescription.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateMedicalPrescriptionCommand implements ICommand {

    private UUID id;
    private UUID hospitalization;
    private String prescriptionDate;
    private String instructions;

    public UpdateMedicalPrescriptionCommand(UUID id, UUID hospitalization, String prescriptionDate, String instructions) {
        this.id = id;
        this.hospitalization = hospitalization;
        this.prescriptionDate = prescriptionDate;
        this.instructions = instructions;
    }

    public static UpdateMedicalPrescriptionCommand fromRequest(UpdateMedicalPrescriptionRequest request, UUID id) {
        return new UpdateMedicalPrescriptionCommand(
                id,
                request.getHospitalization(),
                request.getPrescriptionDate(),
                request.getInstructions()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateMedicalPrescriptionMessage(id);
    }
}
