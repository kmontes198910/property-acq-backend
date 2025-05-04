package com.kynsoft.cirugia.application.query.evolution.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.domain.dto.Evolution;
import com.kynsoft.cirugia.domain.service.IEvolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetEvolutionsBySurgeryIdQueryHandler implements IQueryHandler<GetEvolutionsBySurgeryIdQuery, EvolutionsListResponse> {

    private final IEvolutionRepository evolutionRepository;

    @Override
    public EvolutionsListResponse handle(GetEvolutionsBySurgeryIdQuery query) {
        log.info("Obteniendo evoluciones para la cirugía con ID: {}", query.getSurgeryId());
        
        List<Evolution> evolutions = evolutionRepository.findBySurgeryIdOrderByDateDesc(query.getSurgeryId());
        
        return new EvolutionsListResponse(evolutions);
    }
}