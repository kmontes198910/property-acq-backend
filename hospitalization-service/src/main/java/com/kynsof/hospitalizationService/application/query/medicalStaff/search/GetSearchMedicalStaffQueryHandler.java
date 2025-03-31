package com.kynsof.hospitalizationService.application.query.medicalStaff.search;

import com.kynsof.hospitalizationService.domain.service.IMedicalStaffService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetSearchMedicalStaffQueryHandler implements IQueryHandler<GetSearchMedicalStaffQuery, PaginatedResponse>{

    private final IMedicalStaffService serviceImpl;

    public GetSearchMedicalStaffQueryHandler(IMedicalStaffService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSearchMedicalStaffQuery query) {

        return this.serviceImpl.search(query.getPageable(),query.getFilter());
    }
}
