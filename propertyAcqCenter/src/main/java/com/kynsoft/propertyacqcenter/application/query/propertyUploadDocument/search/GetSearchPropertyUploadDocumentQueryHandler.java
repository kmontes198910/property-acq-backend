package com.kynsoft.propertyacqcenter.application.query.propertyUploadDocument.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyUploadDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPropertyUploadDocumentQueryHandler implements IQueryHandler<GetSearchPropertyUploadDocumentQuery, PaginatedResponse>{

    private final IPropertyUploadDocumentService propertyUploadDocumentService;

    public GetSearchPropertyUploadDocumentQueryHandler(IPropertyUploadDocumentService propertyUploadDocumentService) {
        this.propertyUploadDocumentService = propertyUploadDocumentService;
    }

    @Override
    public PaginatedResponse handle(GetSearchPropertyUploadDocumentQuery query) {

        return this.propertyUploadDocumentService.search(query.getPageable(),query.getFilter());
    }
}
