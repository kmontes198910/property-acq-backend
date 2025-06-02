package com.kynsoft.propertyacqcenter.application.query.document.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchDocumentQueryHandler implements IQueryHandler<GetSearchDocumentQuery, PaginatedResponse> {

    private final IDocumentService documentService;

    public GetSearchDocumentQueryHandler(IDocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public PaginatedResponse handle(GetSearchDocumentQuery query) {
        return this.documentService.search(query.getPageable(), query.getFilter());
    }
}
