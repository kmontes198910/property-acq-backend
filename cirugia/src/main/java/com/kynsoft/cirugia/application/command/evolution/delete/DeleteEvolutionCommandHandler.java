package com.kynsoft.cirugia.application.command.evolution.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.cirugia.domain.service.IEvolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteEvolutionCommandHandler implements ICommandHandler<DeleteEvolutionCommand> {

    private final IEvolutionRepository evolutionRepository;

    @Override
    @Transactional
    public void handle(DeleteEvolutionCommand command) {
        log.info("Eliminando evolución con ID: {}", command.getEvolutionId());
        
        evolutionRepository.deleteById(command.getEvolutionId().toString());
        
        log.info("Evolución eliminada correctamente");
    }
}