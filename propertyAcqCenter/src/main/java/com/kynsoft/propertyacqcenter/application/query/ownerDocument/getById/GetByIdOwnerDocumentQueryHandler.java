package com.kynsoft.propertyacqcenter.application.query.ownerDocument.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.OwnerDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.services.IOwnerDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdOwnerDocumentQueryHandler implements IQueryHandler<GetByIdOwnerDocumentQuery, OwnerDocumentResponse>{

    private final IOwnerDocumentService ownerDocumentService;

    public GetByIdOwnerDocumentQueryHandler(IOwnerDocumentService ownerDocumentService) {
        this.ownerDocumentService = ownerDocumentService;
    }

    @Override
    public OwnerDocumentResponse handle(GetByIdOwnerDocumentQuery query) {

        return new OwnerDocumentResponse(this.ownerDocumentService.findById(query.getId()));
    }
}
