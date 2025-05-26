package com.kynsoft.propertyacqcenter.application.query.propertyDocument.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPropertyDocumentQueryHandler implements IQueryHandler<GetSearchPropertyDocumentQuery, PaginatedResponse>{

    private final IPropertyDocumentService propertyDocumentService;

    public GetSearchPropertyDocumentQueryHandler(IPropertyDocumentService propertyDocumentService) {
        this.propertyDocumentService = propertyDocumentService;
    }

    @Override
    public PaginatedResponse handle(GetSearchPropertyDocumentQuery query) {

        return this.propertyDocumentService.search(query.getPageable(),query.getFilter());
    }
}
