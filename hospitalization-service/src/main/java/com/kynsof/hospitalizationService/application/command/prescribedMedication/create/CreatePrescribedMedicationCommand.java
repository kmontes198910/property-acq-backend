package com.kynsof.hospitalizationService.application.command.prescribedMedication.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePrescribedMedicationCommand implements ICommand {

    private UUID id;
    private UUID medicalPrescription;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String administrationRoute;
    private Integer duration;

    public CreatePrescribedMedicationCommand(UUID medicalPrescription, String medicationName, String dosage, String frequency, String administrationRoute, Integer duration) {
        this.id = UUID.randomUUID();
        this.medicalPrescription = medicalPrescription;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.administrationRoute = administrationRoute;
        this.duration = duration;
    }

    public static CreatePrescribedMedicationCommand fromRequest(CreatePrescribedMedicationRequest request) {
        return new CreatePrescribedMedicationCommand(
                request.getMedicalPrescription(),
                request.getMedicationName(),
                request.getDosage(),
                request.getFrequency(),
                request.getAdministrationRoute(),
                request.getDuration()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreatePrescribedMedicationMessage(id);
    }
}
