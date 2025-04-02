package com.kynsof.hospitalizationService.application.command.prescribedMedication.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdatePrescribedMedicationCommand implements ICommand {

    private UUID id;
    private UUID medicalPrescription;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String administrationRoute;
    private Integer duration;

    public UpdatePrescribedMedicationCommand(UUID id, UUID medicalPrescription, String medicationName, String dosage, String frequency, String administrationRoute, Integer duration) {
        this.id = id;
        this.medicalPrescription = medicalPrescription;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.administrationRoute = administrationRoute;
        this.duration = duration;
    }

    public static UpdatePrescribedMedicationCommand fromRequest(UpdatePrescribedMedicationRequest request, UUID id) {
        return new UpdatePrescribedMedicationCommand(
                id,
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
        return new UpdatePrescribedMedicationMessage(id);
    }
}
