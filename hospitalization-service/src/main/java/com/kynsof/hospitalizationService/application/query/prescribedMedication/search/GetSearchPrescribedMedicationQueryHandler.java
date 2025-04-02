package com.kynsof.hospitalizationService.application.query.prescribedMedication.search;

import com.kynsof.hospitalizationService.domain.service.IPrescribedMedicationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPrescribedMedicationQueryHandler implements IQueryHandler<GetSearchPrescribedMedicationQuery, PaginatedResponse>{

    private final IPrescribedMedicationService serviceImpl;

    public GetSearchPrescribedMedicationQueryHandler(IPrescribedMedicationService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchPrescribedMedicationQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
