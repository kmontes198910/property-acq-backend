package com.kynsoft.propertyacqcenter.application.query.adquisitionProperty.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.AdquisitionPropertyResponse;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdAdquisitionPropertyQueryHandler implements IQueryHandler<GetByIdAdquisitionPropertyQuery, AdquisitionPropertyResponse>{

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public GetByIdAdquisitionPropertyQueryHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public AdquisitionPropertyResponse handle(GetByIdAdquisitionPropertyQuery query) {

        return new AdquisitionPropertyResponse(this.adquisitionPropertyService.findById(query.getId()));
    }
}
