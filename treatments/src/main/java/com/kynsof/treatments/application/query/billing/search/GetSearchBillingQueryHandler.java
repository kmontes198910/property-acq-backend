package com.kynsof.treatments.application.query.billing.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.service.IBillingService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchBillingQueryHandler implements IQueryHandler<GetSearchBillingQuery, PaginatedResponse>{

    private final IBillingService serviceImpl;

    public GetSearchBillingQueryHandler(IBillingService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchBillingQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
