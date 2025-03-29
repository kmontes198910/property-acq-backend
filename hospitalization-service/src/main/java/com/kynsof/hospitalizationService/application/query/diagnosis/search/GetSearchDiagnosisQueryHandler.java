package com.kynsof.hospitalizationService.application.query.diagnosis.search;

import com.kynsof.hospitalizationService.domain.service.IDiagnosisService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchDiagnosisQueryHandler implements IQueryHandler<GetSearchDiagnosisQuery, PaginatedResponse>{

    private final IDiagnosisService serviceImpl;

    public GetSearchDiagnosisQueryHandler(IDiagnosisService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchDiagnosisQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
