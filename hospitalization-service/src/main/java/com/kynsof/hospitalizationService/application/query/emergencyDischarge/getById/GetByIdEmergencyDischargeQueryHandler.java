package com.kynsof.hospitalizationService.application.query.emergencyDischarge.getById;

import com.kynsof.hospitalizationService.application.response.EmergencyDischargeResponse;
import com.kynsof.hospitalizationService.domain.service.IEmergencyDischargeService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdEmergencyDischargeQueryHandler implements IQueryHandler<GetByIdEmergencyDischargeQuery, EmergencyDischargeResponse>{

    private final IEmergencyDischargeService service;

    public GetByIdEmergencyDischargeQueryHandler(IEmergencyDischargeService service) {
        this.service = service;
    }

    @Override
    public EmergencyDischargeResponse handle(GetByIdEmergencyDischargeQuery query) {

        return new EmergencyDischargeResponse(this.service.findById(query.getId()));
    }
}
