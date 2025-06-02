package com.kynsoft.propertyacqcenter.application.query.propertyDocument.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PropertyDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdPropertyDocumentQueryHandler implements IQueryHandler<GetByIdPropertyDocumentQuery, PropertyDocumentResponse>{

    private final IPropertyDocumentService propertyDocumentService;

    public GetByIdPropertyDocumentQueryHandler(IPropertyDocumentService propertyDocumentService) {
        this.propertyDocumentService = propertyDocumentService;
    }

    @Override
    public PropertyDocumentResponse handle(GetByIdPropertyDocumentQuery query) {

        return new PropertyDocumentResponse(this.propertyDocumentService.findById(query.getId()));
    }
}
