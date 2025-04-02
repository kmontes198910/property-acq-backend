package com.kynsof.hospitalizationService.application.query.prescribedMedication.getById;

import com.kynsof.hospitalizationService.application.response.PrescribedMedicationResponse;
import com.kynsof.hospitalizationService.domain.service.IPrescribedMedicationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdPrescribedMedicationQueryHandler implements IQueryHandler<GetByIdPrescribedMedicationQuery, PrescribedMedicationResponse>{

    private final IPrescribedMedicationService service;

    public GetByIdPrescribedMedicationQueryHandler(IPrescribedMedicationService service) {
        this.service = service;
    }

    @Override
    public PrescribedMedicationResponse handle(GetByIdPrescribedMedicationQuery query) {

        return new PrescribedMedicationResponse(this.service.findById(query.getId()));
    }
}
