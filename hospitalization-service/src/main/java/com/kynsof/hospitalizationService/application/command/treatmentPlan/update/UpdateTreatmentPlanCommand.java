package com.kynsof.hospitalizationService.application.command.treatmentPlan.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateTreatmentPlanCommand implements ICommand {

    private UUID id;
    private UUID emergencyCase;
    private String medicationName;
    private String administrationRoute;
    private String dosage;
    private String frequency;
    private Integer daysOfTreatment;

    public UpdateTreatmentPlanCommand(UUID id, UUID emergencyCase, String medicationName, String administrationRoute, String dosage, String frequency, Integer daysOfTreatment) {
        this.id = id;
        this.emergencyCase = emergencyCase;
        this.medicationName = medicationName;
        this.administrationRoute = administrationRoute;
        this.dosage = dosage;
        this.frequency = frequency;
        this.daysOfTreatment = daysOfTreatment;
    }

    public static UpdateTreatmentPlanCommand fromRequest(UpdateTreatmentPlanRequest request, UUID id) {
        return new UpdateTreatmentPlanCommand(
                id,
                request.getEmergencyCase(),
                request.getMedicationName(),
                request.getAdministrationRoute(),
                request.getDosage(),
                request.getFrequency(),
                request.getDaysOfTreatment()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateTreatmentPlanMessage(id);
    }
}
