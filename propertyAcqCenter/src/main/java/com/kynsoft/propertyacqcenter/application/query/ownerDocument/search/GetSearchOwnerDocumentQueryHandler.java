package com.kynsoft.propertyacqcenter.application.query.ownerDocument.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchOwnerDocumentQueryHandler implements IQueryHandler<GetSearchOwnerDocumentQuery, PaginatedResponse>{

    private final IOwnerDocumentService ownerDocumentService;

    public GetSearchOwnerDocumentQueryHandler(IOwnerDocumentService ownerDocumentService) {
        this.ownerDocumentService = ownerDocumentService;
    }

    @Override
    public PaginatedResponse handle(GetSearchOwnerDocumentQuery query) {

        return this.ownerDocumentService.search(query.getPageable(),query.getFilter());
    }
}
