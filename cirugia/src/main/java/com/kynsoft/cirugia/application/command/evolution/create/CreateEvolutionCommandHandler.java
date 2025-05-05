package com.kynsoft.cirugia.application.command.evolution.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.Evolution;
import com.kynsoft.cirugia.domain.service.IEvolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateEvolutionCommandHandler implements ICommandHandler<CreateEvolutionCommand> {

    private final IEvolutionRepository evolutionRepository;

    @Override
    @Transactional
    public void handle(CreateEvolutionCommand command) {
        log.info("Creando nueva evolución para la cirugía ID: {}", command.getSurgeryId());
        
        Evolution evolution = Evolution.builder()
                .id(command.getId())
                .surgeryId(command.getSurgeryId())
                .therapeuticFluids(command.getTherapeuticFluids())
                .prescriptionFluids(command.getPrescriptionFluids())
                .generalCare(command.getGeneralCare())
                .monitoring(command.getMonitoring())
                .diet(command.getDiet())
                .analytics(command.getAnalytics())
                .others(command.getOthers())
                .evolutionDate(LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        Evolution createdEvolution = evolutionRepository.create(evolution);
        command.setId(createdEvolution.getId());
        log.info("Evolución creada con ID: {}", createdEvolution.getId());
    }
}