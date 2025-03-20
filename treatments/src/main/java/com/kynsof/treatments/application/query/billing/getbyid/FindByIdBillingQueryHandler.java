package com.kynsof.treatments.application.query.billing.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.treatments.domain.dto.BillingDto;
import com.kynsof.treatments.domain.service.IBillingService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdBillingQueryHandler implements IQueryHandler<FindByIdBillingQuery, BillingResponse> {

    private final IBillingService serviceImpl;

    public FindByIdBillingQueryHandler(IBillingService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public BillingResponse handle(FindByIdBillingQuery query) {
        BillingDto object = serviceImpl.findById(query.getId());

        return new BillingResponse(object);
    }
}
