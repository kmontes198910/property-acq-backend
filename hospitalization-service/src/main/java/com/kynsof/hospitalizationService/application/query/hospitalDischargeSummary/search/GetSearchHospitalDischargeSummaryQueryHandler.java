package com.kynsof.hospitalizationService.application.query.hospitalDischargeSummary.search;

import com.kynsof.hospitalizationService.domain.service.IHospitalDischargeSummaryService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchHospitalDischargeSummaryQueryHandler implements IQueryHandler<GetSearchHospitalDischargeSummaryQuery, PaginatedResponse>{

    private final IHospitalDischargeSummaryService serviceImpl;

    public GetSearchHospitalDischargeSummaryQueryHandler(IHospitalDischargeSummaryService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchHospitalDischargeSummaryQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
