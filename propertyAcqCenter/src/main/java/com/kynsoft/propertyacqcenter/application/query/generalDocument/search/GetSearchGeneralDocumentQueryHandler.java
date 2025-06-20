package com.kynsoft.propertyacqcenter.application.query.generalDocument.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IGeneralDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchGeneralDocumentQueryHandler implements IQueryHandler<GetSearchGeneralDocumentQuery, PaginatedResponse> {

    private final IGeneralDocumentService generalDocumentService;

    public GetSearchGeneralDocumentQueryHandler(IGeneralDocumentService generalDocumentService) {
        this.generalDocumentService = generalDocumentService;
    }

    @Override
    public PaginatedResponse handle(GetSearchGeneralDocumentQuery query) {
        return this.generalDocumentService.search(query.getPageable(), query.getFilter());
    }
}
