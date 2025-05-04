package com.kynsoft.cirugia.application.query.evolution.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.Evolution;
import com.kynsoft.cirugia.domain.service.IEvolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetEvolutionByIdQueryHandler implements IQueryHandler<GetEvolutionByIdQuery, EvolutionResponse> {

    private final IEvolutionRepository evolutionRepository;

    @Override
    public EvolutionResponse handle(GetEvolutionByIdQuery query) {
        log.info("Obteniendo evolución con ID: {}", query.getId());
        
        Optional<Evolution> evolution = evolutionRepository.findById(query.getId().toString());
        
        if (evolution.isEmpty()) {
            throw new RuntimeException("Evolución no encontrada con ID: " + query.getId());
        }
        
        return new EvolutionResponse(evolution.get());
    }
}