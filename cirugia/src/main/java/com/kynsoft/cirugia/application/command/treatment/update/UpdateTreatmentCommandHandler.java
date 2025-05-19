package com.kynsoft.cirugia.application.command.treatment.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.Treatment;
import com.kynsoft.cirugia.domain.service.ITreatmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateTreatmentCommandHandler implements ICommandHandler<UpdateTreatmentCommand> {

    private final ITreatmentRepository treatmentRepository;

    @Override
    @Transactional
    public void handle(UpdateTreatmentCommand command) {
        log.info("Actualizando tratamiento con ID: {}", command.getId());
        
        Optional<Treatment> existingTreatmentOpt = treatmentRepository.findById(command.getId().toString());
        
        if (existingTreatmentOpt.isEmpty()) {
            throw new RuntimeException("Tratamiento no encontrado con ID: " + command.getId());
        }
        
        Treatment existingTreatment = existingTreatmentOpt.get();
        
        Treatment treatment = Treatment.builder()
                .id(command.getId())
                .surgeryId(existingTreatment.getSurgeryId())
                .name(command.getName() != null ? command.getName() : existingTreatment.getName())
                .description(command.getDescription() != null ? command.getDescription() : existingTreatment.getDescription())
                .quantity(command.getQuantity() != null ? command.getQuantity() : existingTreatment.getQuantity())
                .medicineUnit(command.getMedicineUnit() != null ? command.getMedicineUnit() : existingTreatment.getMedicineUnit())
                .status(command.getStatus() != null ? command.getStatus() : existingTreatment.getStatus())
                .process(command.getProcess() != null ? command.getProcess() : existingTreatment.getProcess())
                .createdAt(existingTreatment.getCreatedAt())
                .createdBy(existingTreatment.getCreatedBy())
                .updatedAt(LocalDateTime.now())
                .updatedBy(command.getUpdatedBy())
                .build();
        
        Treatment updatedTreatment = treatmentRepository.create(treatment);
        log.info("Tratamiento actualizado exitosamente: {}", updatedTreatment.getId());
    }
}