package com.kynsof.hospitalizationService.application.query.treatmentPlan.search;

import com.kynsof.hospitalizationService.domain.service.ITreatmentPlanService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchTreatmentPlanQueryHandler implements IQueryHandler<GetSearchTreatmentPlanQuery, PaginatedResponse>{

    private final ITreatmentPlanService serviceImpl;

    public GetSearchTreatmentPlanQueryHandler(ITreatmentPlanService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchTreatmentPlanQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
