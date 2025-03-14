package com.kynsof.payment.application.query.accountReconciliation.getById;

import com.kynsof.payment.application.query.accountReconciliation.search.AccountReconciliationResponse;
import com.kynsof.payment.domain.dto.AccountReconciliationDto;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.payment.domain.service.IAccountReconciliationService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdAccountReconciliationQueryHandler implements IQueryHandler<FindByIdAccountReconciliationQuery, AccountReconciliationResponse> {

    private final IAccountReconciliationService serviceImpl;

    public FindByIdAccountReconciliationQueryHandler(IAccountReconciliationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public AccountReconciliationResponse handle(FindByIdAccountReconciliationQuery query) {
        AccountReconciliationDto object = serviceImpl.findById(query.getId());

        return new AccountReconciliationResponse(object);
    }
}
