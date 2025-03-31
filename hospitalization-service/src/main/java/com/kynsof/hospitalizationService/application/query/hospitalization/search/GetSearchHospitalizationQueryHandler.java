package com.kynsof.hospitalizationService.application.query.hospitalization.search;

import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchHospitalizationQueryHandler implements IQueryHandler<GetSearchHospitalizationQuery, PaginatedResponse>{

    private final IHospitalizationService serviceImpl;

    public GetSearchHospitalizationQueryHandler(IHospitalizationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchHospitalizationQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
