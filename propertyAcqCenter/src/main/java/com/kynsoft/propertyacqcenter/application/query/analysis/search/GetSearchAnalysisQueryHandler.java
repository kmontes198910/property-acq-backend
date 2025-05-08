package com.kynsoft.propertyacqcenter.application.query.analysis.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IAnalysisService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchAnalysisQueryHandler implements IQueryHandler<GetSearchAnalysisQuery, PaginatedResponse>{

    private final IAnalysisService analysisService;

    public GetSearchAnalysisQueryHandler(IAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @Override
    public PaginatedResponse handle(GetSearchAnalysisQuery query) {

        return this.analysisService.search(query.getPageable(),query.getFilter());
    }
}
