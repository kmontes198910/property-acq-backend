package com.kynsof.hospitalizationService.application.query.medicalPrescription.getById;

import com.kynsof.hospitalizationService.application.response.MedicalPrescriptionResponse;
import com.kynsof.hospitalizationService.domain.service.IMedicalPrescriptionService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdMedicalPrescriptionQueryHandler implements IQueryHandler<GetByIdMedicalPrescriptionQuery, MedicalPrescriptionResponse>{

    private final IMedicalPrescriptionService service;

    public GetByIdMedicalPrescriptionQueryHandler(IMedicalPrescriptionService service) {
        this.service = service;
    }

    @Override
    public MedicalPrescriptionResponse handle(GetByIdMedicalPrescriptionQuery query) {

        return new MedicalPrescriptionResponse(this.service.findById(query.getId()));
    }
}
