package com.kynsof.payment.application.query.accountReconciliation.search;

import com.kynsof.payment.domain.service.IAccountReconciliationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchAccountReconciliationQueryHandler implements IQueryHandler<GetSearchAccountReconciliationQuery, PaginatedResponse>{

    private final IAccountReconciliationService serviceImpl;

    public GetSearchAccountReconciliationQueryHandler(IAccountReconciliationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchAccountReconciliationQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
