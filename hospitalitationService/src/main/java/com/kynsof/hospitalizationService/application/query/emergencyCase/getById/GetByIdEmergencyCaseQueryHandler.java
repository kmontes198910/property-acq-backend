package com.kynsof.hospitalizationService.application.query.emergencyCase.getById;

import com.kynsof.hospitalizationService.application.response.EmergencyCaseResponse;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdEmergencyCaseQueryHandler implements IQueryHandler<GetByIdEmergencyCaseQuery, EmergencyCaseResponse>{

    private final IEmergencyCaseService service;

    public GetByIdEmergencyCaseQueryHandler(IEmergencyCaseService service) {
        this.service = service;
    }

    @Override
    public EmergencyCaseResponse handle(GetByIdEmergencyCaseQuery query) {

        return new EmergencyCaseResponse(this.service.findById(query.getId()));
    }
}
