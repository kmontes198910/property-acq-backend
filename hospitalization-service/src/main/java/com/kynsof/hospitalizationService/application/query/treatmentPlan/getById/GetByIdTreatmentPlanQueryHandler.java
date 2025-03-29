package com.kynsof.hospitalizationService.application.query.treatmentPlan.getById;

import com.kynsof.hospitalizationService.application.response.TreatmentPlanResponse;
import com.kynsof.hospitalizationService.domain.service.ITreatmentPlanService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdTreatmentPlanQueryHandler implements IQueryHandler<GetByIdTreatmentPlanQuery, TreatmentPlanResponse>{

    private final ITreatmentPlanService service;

    public GetByIdTreatmentPlanQueryHandler(ITreatmentPlanService service) {
        this.service = service;
    }

    @Override
    public TreatmentPlanResponse handle(GetByIdTreatmentPlanQuery query) {

        return new TreatmentPlanResponse(this.service.findById(query.getId()));
    }
}
