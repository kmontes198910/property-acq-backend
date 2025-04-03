package com.kynsof.hospitalizationService.application.query.medicalEvolution.search;

import com.kynsof.hospitalizationService.domain.service.IMedicalEvolutionService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchMedicalEvolutionQueryHandler implements IQueryHandler<GetSearchMedicalEvolutionQuery, PaginatedResponse>{

    private final IMedicalEvolutionService serviceImpl;

    public GetSearchMedicalEvolutionQueryHandler(IMedicalEvolutionService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchMedicalEvolutionQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
