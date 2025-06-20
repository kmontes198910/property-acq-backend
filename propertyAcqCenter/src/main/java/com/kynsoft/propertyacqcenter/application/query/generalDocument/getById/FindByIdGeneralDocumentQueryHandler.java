package com.kynsoft.propertyacqcenter.application.query.generalDocument.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.GeneralDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.services.IGeneralDocumentService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdGeneralDocumentQueryHandler implements IQueryHandler<FindByIdGeneralDocumentQuery, GeneralDocumentResponse> {

    private final IGeneralDocumentService generalDocumentService;

    public FindByIdGeneralDocumentQueryHandler(IGeneralDocumentService generalDocumentService) {
        this.generalDocumentService = generalDocumentService;
    }

    @Override
    public GeneralDocumentResponse handle(FindByIdGeneralDocumentQuery query) {
        return new GeneralDocumentResponse(this.generalDocumentService.findById(query.getId()));
    }
}
