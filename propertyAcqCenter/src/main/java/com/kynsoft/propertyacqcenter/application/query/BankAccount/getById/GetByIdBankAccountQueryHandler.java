package com.kynsoft.propertyacqcenter.application.query.BankAccount.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.BankAccountFindByIdResponse;
import com.kynsoft.propertyacqcenter.domain.services.IBankAccountService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdBankAccountQueryHandler implements IQueryHandler<GetByIdBankAccountQuery, BankAccountFindByIdResponse>{

    private final IBankAccountService bankAccountService;

    public GetByIdBankAccountQueryHandler(IBankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public BankAccountFindByIdResponse handle(GetByIdBankAccountQuery query) {

        return new BankAccountFindByIdResponse(this.bankAccountService.findById(query.getId()));
    }
}
