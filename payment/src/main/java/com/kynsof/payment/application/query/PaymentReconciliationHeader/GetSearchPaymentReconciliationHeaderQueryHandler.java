package com.kynsof.payment.application.query.PaymentReconciliationHeader;

import com.kynsof.payment.domain.service.IPaymentReconciliationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPaymentReconciliationHeaderQueryHandler implements IQueryHandler<GetSearchPaymentReconciliationHeaderQuery, PaginatedResponse>{

    private final IPaymentReconciliationService serviceImpl;

    public GetSearchPaymentReconciliationHeaderQueryHandler(IPaymentReconciliationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchPaymentReconciliationHeaderQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
