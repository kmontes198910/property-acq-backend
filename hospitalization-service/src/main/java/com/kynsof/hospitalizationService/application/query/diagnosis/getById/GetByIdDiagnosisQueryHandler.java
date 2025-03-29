package com.kynsof.hospitalizationService.application.query.diagnosis.getById;

import com.kynsof.hospitalizationService.application.response.DiagnosisResponse;
import com.kynsof.hospitalizationService.domain.service.IDiagnosisService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdDiagnosisQueryHandler implements IQueryHandler<GetByIdDiagnosisQuery, DiagnosisResponse>{

    private final IDiagnosisService service;

    public GetByIdDiagnosisQueryHandler(IDiagnosisService service) {
        this.service = service;
    }

    @Override
    public DiagnosisResponse handle(GetByIdDiagnosisQuery query) {

        return new DiagnosisResponse(this.service.findById(query.getId()));
    }
}
