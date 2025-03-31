package com.kynsof.hospitalizationService.application.query.hospitalization.getById;

import com.kynsof.hospitalizationService.application.response.HospitalizationResponse;
import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdHospitalizationQueryHandler implements IQueryHandler<GetByIdHospitalizationQuery, HospitalizationResponse>{

    private final IHospitalizationService service;

    public GetByIdHospitalizationQueryHandler(IHospitalizationService service) {
        this.service = service;
    }

    @Override
    public HospitalizationResponse handle(GetByIdHospitalizationQuery query) {

        return new HospitalizationResponse(this.service.findById(query.getId()));
    }
}
