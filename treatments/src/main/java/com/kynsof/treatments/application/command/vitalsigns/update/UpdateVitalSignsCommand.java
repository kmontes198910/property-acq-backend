package com.kynsof.treatments.application.command.vitalsigns.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateVitalSignsCommand implements ICommand {

    private UUID id; // ID del registro a actualizar
    private final String bloodPressure;
    private final Integer heartRate;
    private final Integer oxygenSaturation;
    private final Integer respiratoryRate;
    private final Double temperature;
    private final Double weight; // Peso en kilogramos
    private final Double height; // Altura en metros o centímetros
    private final Double cranialCircumference; // Circunferencia craneal en centímetros
    private final UUID patientId;

    public UpdateVitalSignsCommand(UUID id, String bloodPressure, Integer heartRate, Integer oxygenSaturation,
                                   Integer respiratoryRate, Double temperature, Double weight, Double height, Double cranialCircumference, UUID patientId) {
        this.id = id;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.oxygenSaturation = oxygenSaturation;
        this.respiratoryRate = respiratoryRate;
        this.temperature = temperature;
        this.weight = weight;
        this.height = height;
        this.cranialCircumference = cranialCircumference;
        this.patientId = patientId;
    }

    public static UpdateVitalSignsCommand fromRequest(UUID id, UpdateVitalSignsRequest request) {
        return new UpdateVitalSignsCommand(
                id,
                request.getBloodPressure(),
                request.getHeartRate(),
                request.getOxygenSaturation(),
                request.getRespiratoryRate(),
                request.getTemperature(),
                request.getWeight(),
                request.getHeight(),
                request.getCranialCircumference()
                , request.getPatientId());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateVitalSignsMessage(id);
    }
}