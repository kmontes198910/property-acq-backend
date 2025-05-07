package com.kynsoft.cirugia.application.command.evolution.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.dto.Evolution;
import com.kynsoft.cirugia.domain.service.IEvolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateEvolutionCommandHandler implements ICommandHandler<UpdateEvolutionCommand> {

    private final IEvolutionRepository evolutionRepository;

    @Override
    @Transactional
    public void handle(UpdateEvolutionCommand command) {
        log.info("Actualizando evolución con ID: {}", command.getId());
        
        Optional<Evolution> existingEvolutionOpt = evolutionRepository.findById(command.getId().toString());
        
        if (existingEvolutionOpt.isEmpty()) {
            throw new RuntimeException("Evolución no encontrada con ID: " + command.getId());
        }
        
        Evolution existingEvolution = existingEvolutionOpt.get();
        
        Evolution evolution = Evolution.builder()
                .id(command.getId())
                .surgeryId(existingEvolution.getSurgeryId())
                .therapeuticFluids(command.getTherapeuticFluids() != null ? command.getTherapeuticFluids() : existingEvolution.getTherapeuticFluids())
                .prescriptionFluids(command.getPrescriptionFluids() != null ? command.getPrescriptionFluids() : existingEvolution.getPrescriptionFluids())
                .generalCare(command.getGeneralCare() != null ? command.getGeneralCare() : existingEvolution.getGeneralCare())
                .monitoring(command.getMonitoring() != null ? command.getMonitoring() : existingEvolution.getMonitoring())
                .diet(command.getDiet() != null ? command.getDiet() : existingEvolution.getDiet())
                .analytics(command.getAnalytics() != null ? command.getAnalytics() : existingEvolution.getAnalytics())
                .others(command.getOthers() != null ? command.getOthers() : existingEvolution.getOthers())
                .process(command.getProcess() != null ? command.getProcess() : existingEvolution.getProcess())
                .createdAt(existingEvolution.getCreatedAt())
                .createdBy(existingEvolution.getCreatedBy())
                .updatedAt(LocalDateTime.now())
                .updatedBy(command.getUpdatedBy())
                .build();
        
        Evolution updatedEvolution = evolutionRepository.create(evolution);
        log.info("Evolución actualizada exitosamente: {}", updatedEvolution.getId());
    }
}