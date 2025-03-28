package com.kynsof.hospitalizationService.application.query.emergencyCase.search;

import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchEmergencyCaseQueryHandler implements IQueryHandler<GetSearchEmergencyCaseQuery, PaginatedResponse>{

    private final IEmergencyCaseService serviceImpl;

    public GetSearchEmergencyCaseQueryHandler(IEmergencyCaseService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchEmergencyCaseQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
