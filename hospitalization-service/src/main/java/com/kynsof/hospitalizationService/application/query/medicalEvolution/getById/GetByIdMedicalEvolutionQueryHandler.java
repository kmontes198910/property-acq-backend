package com.kynsof.hospitalizationService.application.query.medicalEvolution.getById;

import com.kynsof.hospitalizationService.application.response.MedicalEvolutionResponse;
import com.kynsof.hospitalizationService.domain.service.IMedicalEvolutionService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdMedicalEvolutionQueryHandler implements IQueryHandler<GetByIdMedicalEvolutionQuery, MedicalEvolutionResponse>{

    private final IMedicalEvolutionService service;

    public GetByIdMedicalEvolutionQueryHandler(IMedicalEvolutionService service) {
        this.service = service;
    }

    @Override
    public MedicalEvolutionResponse handle(GetByIdMedicalEvolutionQuery query) {

        return new MedicalEvolutionResponse(this.service.findById(query.getId()));
    }
}
