package com.kynsoft.propertyacqcenter.application.query.adquisitionProperty.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.AdquisitionPropertyResponse;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import org.springframework.stereotype.Component;

@Component
public class FindByAdquisitionPropertyByPropertyIdQueryHandler implements IQueryHandler<FindByAdquisitionPropertyByPropertyIdQuery, AdquisitionPropertyResponse> {

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public FindByAdquisitionPropertyByPropertyIdQueryHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public AdquisitionPropertyResponse handle(FindByAdquisitionPropertyByPropertyIdQuery query) {
        return new AdquisitionPropertyResponse(this.adquisitionPropertyService.findByPropertyId(query.getId()));
    }
}
