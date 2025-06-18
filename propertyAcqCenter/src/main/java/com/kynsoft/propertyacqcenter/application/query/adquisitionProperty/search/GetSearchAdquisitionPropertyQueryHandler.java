package com.kynsoft.propertyacqcenter.application.query.adquisitionProperty.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchAdquisitionPropertyQueryHandler implements IQueryHandler<GetSearchAdquisitionPropertyQuery, PaginatedResponse>{

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public GetSearchAdquisitionPropertyQueryHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public PaginatedResponse handle(GetSearchAdquisitionPropertyQuery query) {

        return this.adquisitionPropertyService.search(query.getPageable(),query.getFilter());
    }
}
