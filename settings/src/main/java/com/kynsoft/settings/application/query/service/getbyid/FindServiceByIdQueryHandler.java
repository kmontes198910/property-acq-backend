package com.kynsoft.settings.application.query.service.getbyid;


import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.settings.application.response.ServicesResponse;
import com.kynsoft.settings.domain.dto.ServiceDto;
import com.kynsoft.settings.domain.services.IServiceService;
import org.springframework.stereotype.Component;

@Component
public class FindServiceByIdQueryHandler implements IQueryHandler<FindServiceByIdQuery, ServicesResponse> {

    private final IServiceService service;

    public FindServiceByIdQueryHandler(IServiceService service) {
        this.service = service;
    }

    @Override
    public ServicesResponse handle(FindServiceByIdQuery query) {
        ServiceDto response = service.findByIds(query.getId());

        return new ServicesResponse(response);
    }
}
