package com.kynsoft.propertyacqcenter.application.query.propertyUploadDocument.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PropertyUploadDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyUploadDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdPropertyUploadDocumentQueryHandler implements IQueryHandler<GetByIdPropertyUploadDocumentQuery, PropertyUploadDocumentResponse>{

    private final IPropertyUploadDocumentService propertyUploadDocumentService;

    public GetByIdPropertyUploadDocumentQueryHandler(IPropertyUploadDocumentService propertyUploadDocumentService) {
        this.propertyUploadDocumentService = propertyUploadDocumentService;
    }

    @Override
    public PropertyUploadDocumentResponse handle(GetByIdPropertyUploadDocumentQuery query) {

        return new PropertyUploadDocumentResponse(this.propertyUploadDocumentService.findById(query.getId()));
    }
}
