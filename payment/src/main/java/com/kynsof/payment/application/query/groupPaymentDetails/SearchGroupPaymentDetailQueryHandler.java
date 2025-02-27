package com.kynsof.payment.application.query.groupPaymentDetails;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import org.springframework.stereotype.Component;

@Component
public class SearchGroupPaymentDetailQueryHandler implements IQueryHandler<SearchGroupPaymentDetailQuery, PaginatedResponse>{

    private final IGroupPaymentService serviceImpl;

    public SearchGroupPaymentDetailQueryHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(SearchGroupPaymentDetailQuery query) {
        return this.serviceImpl.searchPaymentDetail(query.getPageable(),query.getFilter());
    }
}
