package com.kynsoft.settings.application.query.serviceType.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.settings.application.response.ServiceTypeResponse;
import com.kynsoft.settings.domain.dto.ServiceTypeDto;
import com.kynsoft.settings.domain.services.IServiceTypeService;
import org.springframework.stereotype.Component;

@Component
public class FindServiceTypeByIdQueryHandler implements IQueryHandler<FindServiceTypeByIdQuery, ServiceTypeResponse> {

    private final IServiceTypeService service;

    public FindServiceTypeByIdQueryHandler(IServiceTypeService service) {
        this.service = service;
    }

    @Override
    public ServiceTypeResponse handle(FindServiceTypeByIdQuery query) {
        ServiceTypeDto response = service.findById(query.getId());

        return new ServiceTypeResponse(response);
    }
}
