package com.kynsof.payment.application.query.client.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.payment.domain.service.IClientService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchClientQueryHandler implements IQueryHandler<GetSearchClientQuery, PaginatedResponse>{

    private final IClientService serviceImpl;

    public GetSearchClientQueryHandler(IClientService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchClientQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
