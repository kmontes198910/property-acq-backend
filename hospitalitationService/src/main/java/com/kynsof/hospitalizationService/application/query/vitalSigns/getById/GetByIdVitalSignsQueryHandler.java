package com.kynsof.hospitalizationService.application.query.vitalSigns.getById;

import com.kynsof.hospitalizationService.application.response.VitalSignsResponse;
import com.kynsof.hospitalizationService.domain.service.IVitalSignsService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdVitalSignsQueryHandler implements IQueryHandler<GetByIdVitalSignsQuery, VitalSignsResponse>{

    private final IVitalSignsService service;

    public GetByIdVitalSignsQueryHandler(IVitalSignsService service) {
        this.service = service;
    }

    @Override
    public VitalSignsResponse handle(GetByIdVitalSignsQuery query) {

        return new VitalSignsResponse(this.service.findById(query.getId()));
    }
}
