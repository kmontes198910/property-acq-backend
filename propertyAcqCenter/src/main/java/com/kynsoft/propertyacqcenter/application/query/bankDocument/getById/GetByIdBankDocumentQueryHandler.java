package com.kynsoft.propertyacqcenter.application.query.bankDocument.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.BankDocumentResponse;
import com.kynsoft.propertyacqcenter.domain.services.IBankDocumentService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdBankDocumentQueryHandler implements IQueryHandler<GetByIdBankDocumentQuery, BankDocumentResponse>{

    private final IBankDocumentService bankDocumentService;

    public GetByIdBankDocumentQueryHandler(IBankDocumentService bankDocumentService) {
        this.bankDocumentService = bankDocumentService;
    }

    @Override
    public BankDocumentResponse handle(GetByIdBankDocumentQuery query) {

        return new BankDocumentResponse(this.bankDocumentService.findById(query.getId()));
    }
}
