package com.kynsoft.propertyacqcenter.application.query.BankAccount.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IBankAccountService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchBankAccountQueryHandler implements IQueryHandler<GetSearchBankAccountQuery, PaginatedResponse>{

    private final IBankAccountService bankAccountService;

    public GetSearchBankAccountQueryHandler(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public PaginatedResponse handle(GetSearchBankAccountQuery query) {

        return this.bankAccountService.search(query.getPageable(),query.getFilter());
    }
}
