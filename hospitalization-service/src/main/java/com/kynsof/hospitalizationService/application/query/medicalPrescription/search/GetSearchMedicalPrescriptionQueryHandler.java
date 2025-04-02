package com.kynsof.hospitalizationService.application.query.medicalPrescription.search;

import com.kynsof.hospitalizationService.domain.service.IMedicalPrescriptionService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchMedicalPrescriptionQueryHandler implements IQueryHandler<GetSearchMedicalPrescriptionQuery, PaginatedResponse>{

    private final IMedicalPrescriptionService serviceImpl;

    public GetSearchMedicalPrescriptionQueryHandler(IMedicalPrescriptionService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchMedicalPrescriptionQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
