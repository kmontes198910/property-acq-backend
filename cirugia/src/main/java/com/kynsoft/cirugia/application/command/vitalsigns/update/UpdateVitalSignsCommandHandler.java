package com.kynsoft.cirugia.application.command.vitalsigns.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.VitalSigns;
import com.kynsoft.cirugia.domain.service.IVitalSignsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateVitalSignsCommandHandler implements ICommandHandler<UpdateVitalSignsCommand> {

    private final IVitalSignsService vitalSignsService;

    @Override
    public void handle(UpdateVitalSignsCommand command) {
        log.info("Updating vital signs with ID: {}", command.getId());
        
        Optional<VitalSigns> vitalSignsOptional = vitalSignsService.findById(command.getId());
        
        if (vitalSignsOptional.isEmpty()) {
            throw new RuntimeException("Vital signs not found with ID: " + command.getId());
        }
        
        VitalSigns existingVitalSigns = vitalSignsOptional.get();
        
        // Actualizar solo los campos no nulos del comando
        if (command.getPatientId() != null) {
            existingVitalSigns.setPatientId(command.getPatientId());
        }
        
        if (command.getSurgeryId() != null) {
            existingVitalSigns.setSurgeryId(command.getSurgeryId());
        }

        
        if (command.getSystolicBloodPressure() != null) {
            existingVitalSigns.setSystolicBloodPressure(command.getSystolicBloodPressure());
        }
        
        if (command.getDiastolicBloodPressure() != null) {
            existingVitalSigns.setDiastolicBloodPressure(command.getDiastolicBloodPressure());
        }
        
        if (command.getHeartRate() != null) {
            existingVitalSigns.setHeartRate(command.getHeartRate());
        }

        if(command.getProcess() != null) {
            existingVitalSigns.setProcess(command.getProcess());
        }
        
        if (command.getRespiratoryRate() != null) {
            existingVitalSigns.setRespiratoryRate(command.getRespiratoryRate());
        }
        
        if (command.getTemperature() != null) {
            existingVitalSigns.setTemperature(command.getTemperature());
        }
        
        if (command.getOxygenSaturation() != null) {
            existingVitalSigns.setOxygenSaturation(command.getOxygenSaturation());
        }
        
        if (command.getWeight() != null) {
            existingVitalSigns.setWeight(command.getWeight());
        }
        
        if (command.getHeight() != null) {
            existingVitalSigns.setHeight(command.getHeight());
        }
        
        if (command.getCapillaryGlucose() != null) {
            existingVitalSigns.setCapillaryGlucose(command.getCapillaryGlucose());
        }
        
        if (command.getGlasgowScoreMotor() != null) {
            existingVitalSigns.setGlasgowScoreMotor(command.getGlasgowScoreMotor());
        }
        
        if (command.getGlasgowScoreVerbal() != null) {
            existingVitalSigns.setGlasgowScoreVerbal(command.getGlasgowScoreVerbal());
        }
        
        if (command.getGlasgowScoreOcular() != null) {
            existingVitalSigns.setGlasgowScoreOcular(command.getGlasgowScoreOcular());
        }
        
        if (command.getObservations() != null) {
            existingVitalSigns.setObservations(command.getObservations());
        }
        
        if (command.getRecordedAt() != null) {
            existingVitalSigns.setRecordedAt(command.getRecordedAt());
        }
        
        existingVitalSigns.setUpdatedAt(LocalDateTime.now());
        existingVitalSigns.setUpdatedBy(command.getUpdatedBy());
        
        VitalSigns updatedVitalSigns = vitalSignsService.update(existingVitalSigns);

    }
}