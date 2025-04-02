package com.kynsof.hospitalizationService.application.query.bed.getById;

import com.kynsof.hospitalizationService.application.response.BedResponse;
import com.kynsof.hospitalizationService.domain.service.IBedService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import org.springframework.stereotype.Component;

@Component
public class GetByIdBedQueryHandler implements IQueryHandler<GetByIdBedQuery, BedResponse>{

    private final IBedService service;

    public GetByIdBedQueryHandler(IBedService service) {
        this.service = service;
    }

    @Override
    public BedResponse handle(GetByIdBedQuery query) {

        return new BedResponse(this.service.findById(query.getId()));
    }
}
