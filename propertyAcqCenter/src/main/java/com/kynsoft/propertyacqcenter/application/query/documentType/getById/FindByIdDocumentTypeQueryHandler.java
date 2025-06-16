package com.kynsoft.propertyacqcenter.application.query.documentType.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.DocumentTypeResponse;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdDocumentTypeQueryHandler implements IQueryHandler<FindByIdDocumentTypeQuery, DocumentTypeResponse> {

    private final IDocumentTypeService documentTypeService;

    public FindByIdDocumentTypeQueryHandler(IDocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @Override
    public DocumentTypeResponse handle(FindByIdDocumentTypeQuery query) {
        return new DocumentTypeResponse(this.documentTypeService.findById(query.getId()));
    }
}
