package com.kynsoft.propertyacqcenter.application.query.document.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.DocumentFindByIdResponse;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdDocumentQueryHandler implements IQueryHandler<GetByIdDocumentQuery, DocumentFindByIdResponse>{

    private final IDocumentService documentService;

    public GetByIdDocumentQueryHandler(IDocumentService documentService) {
        this.documentService = documentService;
    }

    @Override
    public DocumentFindByIdResponse handle(GetByIdDocumentQuery query) {

        DocumentFindByIdResponse response = new DocumentFindByIdResponse(this.documentService.findById(query.getId()));
        System.err.println("##################################################");
        System.err.println("##################################################");
        System.err.println("" + response.getLegalEntity().getName());
        System.err.println("##################################################");
        return response;
    }
}
