package com.kynsoft.cirugia.application.query.evolution.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.service.IEvolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchEvolutionsQueryHandler implements IQueryHandler<SearchEvolutionsQuery, PaginatedResponse> {

    private final IEvolutionRepository evolutionRepository;

    @Override
    public PaginatedResponse handle(SearchEvolutionsQuery query) {
        log.info("Buscando evoluciones con criterios de filtro: {}", query.getFilterCriteria());
        
        return evolutionRepository.search(query.getPageable(), query.getFilterCriteria());
    }
}