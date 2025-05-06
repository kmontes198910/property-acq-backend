package com.kynsoft.cirugia.application.command.vitalsigns.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.VitalSigns;
import com.kynsoft.cirugia.domain.service.IVitalSignsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateVitalSignsCommandHandler implements ICommandHandler<CreateVitalSignsCommand> {

    private final IVitalSignsService vitalSignsService;

    @Override
    public void handle(CreateVitalSignsCommand command) {
        log.info("Creating vital signs for patient ID: {}", command.getPatientId());
        
        VitalSigns vitalSigns = VitalSigns.builder()
                .patientId(command.getPatientId())
                .surgeryId(command.getSurgeryId())
                .systolicBloodPressure(command.getSystolicBloodPressure())
                .diastolicBloodPressure(command.getDiastolicBloodPressure())
                .heartRate(command.getHeartRate())
                .respiratoryRate(command.getRespiratoryRate())
                .temperature(command.getTemperature())
                .oxygenSaturation(command.getOxygenSaturation())
                .weight(command.getWeight())
                .height(command.getHeight())
                .capillaryGlucose(command.getCapillaryGlucose())
                .glasgowScoreMotor(command.getGlasgowScoreMotor())
                .glasgowScoreVerbal(command.getGlasgowScoreVerbal())
                .glasgowScoreOcular(command.getGlasgowScoreOcular())
                .observations(command.getObservations())
                .recordedAt(command.getRecordedAt() != null ? command.getRecordedAt() : LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .build();
        
        VitalSigns createdVitalSigns = vitalSignsService.create(vitalSigns);
        
        command.setId(createdVitalSigns.getId());
    }
}