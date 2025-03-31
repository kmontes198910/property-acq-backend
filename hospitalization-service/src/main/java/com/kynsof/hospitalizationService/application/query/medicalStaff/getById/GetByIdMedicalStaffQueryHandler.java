package com.kynsof.hospitalizationService.application.query.medicalStaff.getById;

import com.kynsof.hospitalizationService.application.response.MedicalStaffResponse;
import com.kynsof.hospitalizationService.domain.service.IMedicalStaffService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdMedicalStaffQueryHandler implements IQueryHandler<GetByIdMedicalStaffQuery, MedicalStaffResponse>{

    private final IMedicalStaffService service;

    public GetByIdMedicalStaffQueryHandler(IMedicalStaffService service) {
        this.service = service;
    }

    @Override
    public MedicalStaffResponse handle(GetByIdMedicalStaffQuery query) {

        return new MedicalStaffResponse(this.service.findById(query.getId()));
    }
}
