package com.kynsof.hospitalizationService.application.command.treatmentPlan.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateTreatmentPlanCommand implements ICommand {

    private UUID id;
    private UUID emergencyCase;
    private String medicationName;
    private String administrationRoute;
    private String dosage;
    private String frequency;
    private Integer daysOfTreatment;

    public CreateTreatmentPlanCommand(UUID emergencyCase, String medicationName, String administrationRoute, String dosage, String frequency, Integer daysOfTreatment) {
        this.id = UUID.randomUUID();
        this.emergencyCase = emergencyCase;
        this.medicationName = medicationName;
        this.administrationRoute = administrationRoute;
        this.dosage = dosage;
        this.frequency = frequency;
        this.daysOfTreatment = daysOfTreatment;
    }

    public static CreateTreatmentPlanCommand fromRequest(CreateTreatmentPlanRequest request) {
        return new CreateTreatmentPlanCommand(
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
        return new CreateTreatmentPlanMessage(id);
    }
}
