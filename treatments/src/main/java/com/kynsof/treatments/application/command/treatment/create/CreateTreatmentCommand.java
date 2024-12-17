package com.kynsof.treatments.application.command.treatment.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateTreatmentCommand implements ICommand {
    private UUID id;
    private final String description;
    private final String medication;
    private final int quantity;
    private final String medicineUnit;
    private final String externalConsultId;

    public CreateTreatmentCommand(String description, String medication, int quantity, String medicineUnit, String externalConsultId) {
        this.description = description;
        this.medication = medication;
        this.quantity = quantity;
        this.medicineUnit = medicineUnit;
        this.externalConsultId = externalConsultId;
    }
    public static CreateTreatmentCommand fromRequest(CreateTreatmentRequest request) {
        return new CreateTreatmentCommand(
                request.getDescription(),
                request.getMedication(),
                request.getQuantity(),
                request.getMedicineUnit(),
                request.getExternalConsultId()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateTreatmentMessage(id);
    }


}
