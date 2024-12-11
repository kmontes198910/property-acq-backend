package com.kynsof.treatments.application.query.vitalsigns.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.treatments.application.query.vitalsigns.search.VitalSignsResponse;
import com.kynsof.treatments.domain.dto.VitalSignsDto;
import com.kynsof.treatments.domain.service.IVitalSignsService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdVitalSignsQueryHandler implements IQueryHandler<FindByIdVitalSignsQuery, VitalSignsResponse> {

    private final IVitalSignsService serviceImpl;

    public FindByIdVitalSignsQueryHandler(IVitalSignsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public VitalSignsResponse handle(FindByIdVitalSignsQuery query) {
        VitalSignsDto contactInfoDto = serviceImpl.findById(query.getId());

        return new VitalSignsResponse(contactInfoDto);
    }
}
