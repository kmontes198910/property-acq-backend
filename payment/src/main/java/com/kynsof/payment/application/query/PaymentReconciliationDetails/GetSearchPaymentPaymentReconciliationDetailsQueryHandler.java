package com.kynsof.payment.application.query.PaymentReconciliationDetails;

import com.kynsof.payment.domain.service.IPaymentReconciliationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPaymentPaymentReconciliationDetailsQueryHandler implements IQueryHandler<GetSearchPaymentPaymentReconciliationDetailsQuery, PaginatedResponse>{

    private final IPaymentReconciliationService serviceImpl;

    public GetSearchPaymentPaymentReconciliationDetailsQueryHandler(IPaymentReconciliationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchPaymentPaymentReconciliationDetailsQuery query) {

        return this.serviceImpl.searchReconciliationDetail(query.getPageable(),query.getFilter());
    }
}
