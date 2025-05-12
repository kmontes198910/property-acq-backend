package com.kynsoft.propertyacqcenter.application.query.bankDocument.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IBankDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchBankDocumentQueryHandler implements IQueryHandler<GetSearchBankDocumentQuery, PaginatedResponse>{

    private final IBankDocumentService bankDocumentService;

    public GetSearchBankDocumentQueryHandler(IBankDocumentService bankDocumentService) {
        this.bankDocumentService = bankDocumentService;
    }

    @Override
    public PaginatedResponse handle(GetSearchBankDocumentQuery query) {

        return this.bankDocumentService.search(query.getPageable(),query.getFilter());
    }
}
