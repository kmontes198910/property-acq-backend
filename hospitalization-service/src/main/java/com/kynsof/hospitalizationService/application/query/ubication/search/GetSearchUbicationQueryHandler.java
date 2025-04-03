package com.kynsof.hospitalizationService.application.query.ubication.search;

import com.kynsof.hospitalizationService.domain.service.IUbicationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchUbicationQueryHandler implements IQueryHandler<GetSearchUbicationQuery, PaginatedResponse>{

    private final IUbicationService serviceImpl;

    public GetSearchUbicationQueryHandler(IUbicationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchUbicationQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
