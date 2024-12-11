package com.kynsof.treatments.application.command.vitalsigns.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateVitalSignsCommand implements ICommand {

    private UUID id;
    private final UUID patientId;
    private final String bloodPressure;
    private final Integer heartRate;
    private final Integer oxygenSaturation;
    private final Integer respiratoryRate;
    private final Double temperature;
    private final Double weight; // Peso en kilogramos
    private final Double height; // Altura en metros o centímetros
    private final Double cranialCircumference; // Circunferencia craneal en centímetros

    public CreateVitalSignsCommand(UUID patientId, String bloodPressure, Integer heartRate, Integer oxygenSaturation,
                                   Integer respiratoryRate, Double temperature, Double weight, Double height, Double cranialCircumference) {
        this.patientId = patientId;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.oxygenSaturation = oxygenSaturation;
        this.respiratoryRate = respiratoryRate;
        this.temperature = temperature;
        this.weight = weight;
        this.height = height;
        this.cranialCircumference = cranialCircumference;
    }

    public static CreateVitalSignsCommand fromRequest(CreateVitalSignsRequest request) {
        return new CreateVitalSignsCommand(
                request.getPatientId(), request.getBloodPressure(), request.getHeartRate(),
                request.getOxygenSaturation(), request.getRespiratoryRate(), request.getTemperature(),
                request.getWeight(), request.getHeight(), request.getCranialCircumference());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateVitalSignsMessage(id);
    }
}