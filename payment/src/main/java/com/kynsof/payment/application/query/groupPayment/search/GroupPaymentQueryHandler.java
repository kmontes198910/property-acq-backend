package com.kynsof.payment.application.query.groupPayment.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import org.springframework.stereotype.Component;

@Component
public class GroupPaymentQueryHandler implements IQueryHandler<GroupPaymentQuery, PaginatedResponse>{

    private final IGroupPaymentService serviceImpl;

    public GroupPaymentQueryHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GroupPaymentQuery query) {
        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
