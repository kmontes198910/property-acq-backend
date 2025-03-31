package com.kynsof.hospitalizationService.application.query.responsibleMedicalStaff.search;

import com.kynsof.hospitalizationService.domain.service.IResponsibleMedicalStaffService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchResponsibleMedicalStaffQueryHandler implements IQueryHandler<GetSearchResponsibleMedicalStaffQuery, PaginatedResponse>{

    private final IResponsibleMedicalStaffService serviceImpl;

    public GetSearchResponsibleMedicalStaffQueryHandler(IResponsibleMedicalStaffService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchResponsibleMedicalStaffQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
