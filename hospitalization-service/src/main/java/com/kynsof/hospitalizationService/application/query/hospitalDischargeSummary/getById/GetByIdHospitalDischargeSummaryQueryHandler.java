package com.kynsof.hospitalizationService.application.query.hospitalDischargeSummary.getById;

import com.kynsof.hospitalizationService.application.response.HospitalDischargeSummaryResponse;
import com.kynsof.hospitalizationService.domain.service.IHospitalDischargeSummaryService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdHospitalDischargeSummaryQueryHandler implements IQueryHandler<GetByIdHospitalDischargeSummaryQuery, HospitalDischargeSummaryResponse>{

    private final IHospitalDischargeSummaryService service;

    public GetByIdHospitalDischargeSummaryQueryHandler(IHospitalDischargeSummaryService service) {
        this.service = service;
    }

    @Override
    public HospitalDischargeSummaryResponse handle(GetByIdHospitalDischargeSummaryQuery query) {

        return new HospitalDischargeSummaryResponse(this.service.findById(query.getId()));
    }
}
