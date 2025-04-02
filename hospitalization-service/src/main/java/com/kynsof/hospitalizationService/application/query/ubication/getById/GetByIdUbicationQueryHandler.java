package com.kynsof.hospitalizationService.application.query.ubication.getById;

import com.kynsof.hospitalizationService.application.response.UbicationResponse;
import com.kynsof.hospitalizationService.domain.service.IUbicationService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdUbicationQueryHandler implements IQueryHandler<GetByIdUbicationQuery, UbicationResponse>{

    private final IUbicationService service;

    public GetByIdUbicationQueryHandler(IUbicationService service) {
        this.service = service;
    }

    @Override
    public UbicationResponse handle(GetByIdUbicationQuery query) {

        return new UbicationResponse(this.service.findById(query.getId()));
    }
}
