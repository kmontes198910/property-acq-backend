package com.kynsoft.cirugia.application.command.vitalsigns.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateVitalSignsCommand implements ICommand {
    private UUID id;
    private UUID patientId;
    private UUID surgeryId;
    private Integer systolicBloodPressure;
    private Integer diastolicBloodPressure;
    private Integer heartRate;
    private Integer respiratoryRate;
    private Double temperature;
    private Double oxygenSaturation;
    private Double weight;
    private Double height;
    private Double capillaryGlucose;
    private Integer glasgowScoreMotor;
    private Integer glasgowScoreVerbal;
    private Integer glasgowScoreOcular;
    private String observations;
    private LocalDateTime recordedAt;
    private UUID createdBy;
    private String process;
    
    public static CreateVitalSignsCommand fromRequest(CreateVitalSignsRequest request, UUID createdBy   ) {
        return CreateVitalSignsCommand.builder()
                .patientId(request.getPatientId())
                .surgeryId(request.getSurgeryId())
                .systolicBloodPressure(request.getSystolicBloodPressure())
                .diastolicBloodPressure(request.getDiastolicBloodPressure())
                .heartRate(request.getHeartRate())
                .respiratoryRate(request.getRespiratoryRate())
                .temperature(request.getTemperature())
                .oxygenSaturation(request.getOxygenSaturation())
                .weight(request.getWeight())
                .height(request.getHeight())
                .capillaryGlucose(request.getCapillaryGlucose())
                .glasgowScoreMotor(request.getGlasgowScoreMotor())
                .glasgowScoreVerbal(request.getGlasgowScoreVerbal())
                .glasgowScoreOcular(request.getGlasgowScoreOcular())
                .observations(request.getObservations())
                .recordedAt(LocalDateTime.now())
                .process(request.getProcess())
                .createdBy(createdBy)
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateVitalSignsMessage(id);
    }
}