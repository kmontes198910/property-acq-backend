package com.kynsof.hospitalizationService.application.query.responsibleMedicalStaff.getById;

import com.kynsof.hospitalizationService.application.response.ResponsibleMedicalStaffResponse;
import com.kynsof.hospitalizationService.domain.service.IResponsibleMedicalStaffService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdResponsibleMedicalStaffQueryHandler implements IQueryHandler<GetByIdResponsibleMedicalStaffQuery, ResponsibleMedicalStaffResponse>{

    private final IResponsibleMedicalStaffService service;

    public GetByIdResponsibleMedicalStaffQueryHandler(IResponsibleMedicalStaffService service) {
        this.service = service;
    }

    @Override
    public ResponsibleMedicalStaffResponse handle(GetByIdResponsibleMedicalStaffQuery query) {

        return new ResponsibleMedicalStaffResponse(this.service.findById(query.getId()));
    }
}
