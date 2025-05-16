package com.kynsoft.cirugia.application.command.treatment.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.Treatment;
import com.kynsoft.cirugia.domain.service.ITreatmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateTreatmentCommandHandler implements ICommandHandler<CreateTreatmentCommand> {

    private final ITreatmentRepository treatmentRepository;

    @Override
    @Transactional
    public void handle(CreateTreatmentCommand command) {
        log.info("Creando nuevo tratamiento para paciente ID: {} " + 
                (command.getSurgeryId() != null ? "y cirugía ID: {}" : "sin cirugía asociada"), 
                command.getPatientId(), command.getSurgeryId());
        
        Treatment treatment = Treatment.builder()
                .id(command.getId())
                .surgeryId(command.getSurgeryId())
                .patientId(command.getPatientId())
                .code(command.getCode())
                .name(command.getName())
                .description(command.getDescription())
                .quantity(command.getQuantity())
                .medicineUnit(command.getMedicineUnit())
                .status(command.getStatus())
                .process(command.getProcess())
                .createdBy(command.getCreatedBy())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Treatment createdTreatment = treatmentRepository.create(treatment);
        command.setId(createdTreatment.getId());
        log.info("Tratamiento creado con ID: {}", createdTreatment.getId());
    }
}