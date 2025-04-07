package com.kynsof.hospitalizationService.application.query.emergencyCase.getBedById;

import com.kynsof.hospitalizationService.application.response.EmergencyCaseAndBedResponse;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdEmergencyCaseAndBedQueryHandler implements IQueryHandler<GetByIdEmergencyCaseAndBedQuery, EmergencyCaseAndBedResponse>{

    private final IEmergencyCaseService service;

    public GetByIdEmergencyCaseAndBedQueryHandler(IEmergencyCaseService service) {
        this.service = service;
    }

    @Override
    public EmergencyCaseAndBedResponse handle(GetByIdEmergencyCaseAndBedQuery query) {

        return this.service.getBedByEmergencyCaseId(query.getId());
    }
}
