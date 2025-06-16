package com.kynsoft.propertyacqcenter.application.query.documentType.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchDocumentTypeQueryHandler implements IQueryHandler<GetSearchDocumentTypeQuery, PaginatedResponse> {

    private final IDocumentTypeService documentTypeService;

    public GetSearchDocumentTypeQueryHandler(IDocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @Override
    public PaginatedResponse handle(GetSearchDocumentTypeQuery query) {
        return this.documentTypeService.search(query.getPageable(), query.getFilter());
    }
}
