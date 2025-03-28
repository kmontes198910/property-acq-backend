package com.kynsof.hospitalizationService.application.query.vitalSigns.search;

import com.kynsof.hospitalizationService.domain.service.IVitalSignsService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchVitalSignsQueryHandler implements IQueryHandler<GetSearchVitalSignsQuery, PaginatedResponse>{

    private final IVitalSignsService serviceImpl;

    public GetSearchVitalSignsQueryHandler(IVitalSignsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchVitalSignsQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
