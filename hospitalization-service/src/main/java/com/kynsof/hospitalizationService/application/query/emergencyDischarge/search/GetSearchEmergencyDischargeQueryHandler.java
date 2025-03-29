package com.kynsof.hospitalizationService.application.query.emergencyDischarge.search;

import com.kynsof.hospitalizationService.domain.service.IEmergencyDischargeService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchEmergencyDischargeQueryHandler implements IQueryHandler<GetSearchEmergencyDischargeQuery, PaginatedResponse>{

    private final IEmergencyDischargeService serviceImpl;

    public GetSearchEmergencyDischargeQueryHandler(IEmergencyDischargeService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchEmergencyDischargeQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
