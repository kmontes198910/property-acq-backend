package com.kynsof.hospitalizationService.application.command.vitalSigns.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateVitalSignsCommand implements ICommand {

    private UUID id;
    private UUID emergencyCase;
    private Double systolicBloodPressure;
    private Double diastolicBloodPressure;
    private Integer heartRate;
    private Integer respiratoryRate;
    private Double temperature;
    private Double weight;
    private Double height;
    private Double capillaryGlucose;
    private Integer glasgowScoreOcular;
    private Integer glasgowScoreVerbal;
    private Integer glasgowScoreMotor;

    public UpdateVitalSignsCommand(UUID id, 
                                   UUID emergencyCase, 
                                   Double systolicBloodPressure, 
                                   Double diastolicBloodPressure, 
                                   Integer heartRate, 
                                   Integer respiratoryRate, 
                                   Double temperature, 
                                   Double weight, 
                                   Double height, 
                                   Double capillaryGlucose, 
                                   Integer glasgowScoreOcular, 
                                   Integer glasgowScoreVerbal, 
                                   Integer glasgowScoreMotor) {
        this.id = id;
        this.emergencyCase = emergencyCase;
        this.systolicBloodPressure = systolicBloodPressure;
        this.diastolicBloodPressure = diastolicBloodPressure;
        this.heartRate = heartRate;
        this.respiratoryRate = respiratoryRate;
        this.temperature = temperature;
        this.weight = weight;
        this.height = height;
        this.capillaryGlucose = capillaryGlucose;
        this.glasgowScoreOcular = glasgowScoreOcular;
        this.glasgowScoreVerbal = glasgowScoreVerbal;
        this.glasgowScoreMotor = glasgowScoreMotor;
    }

    public static UpdateVitalSignsCommand fromRequest(UpdateVitalSignsRequest request, UUID id) {
        return new UpdateVitalSignsCommand(
                id,
                request.getEmergencyCase(),
                request.getSystolicBloodPressure(),
                request.getDiastolicBloodPressure(),
                request.getHeartRate(),
                request.getRespiratoryRate(),
                request.getTemperature(),
                request.getWeight(),
                request.getHeight(),
                request.getCapillaryGlucose(),
                request.getGlasgowScoreOcular(),
                request.getGlasgowScoreVerbal(),
                request.getGlasgowScoreMotor()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateVitalSignsMessage(id);
    }
}
