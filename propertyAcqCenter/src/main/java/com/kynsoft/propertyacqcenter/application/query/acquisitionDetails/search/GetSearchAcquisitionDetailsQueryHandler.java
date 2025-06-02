package com.kynsoft.propertyacqcenter.application.query.acquisitionDetails.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IAcquisitionDetailsService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchAcquisitionDetailsQueryHandler implements IQueryHandler<GetSearchAcquisitionDetailsQuery, PaginatedResponse>{

    private final IAcquisitionDetailsService acquisitionDetailsService;

    public GetSearchAcquisitionDetailsQueryHandler(IAcquisitionDetailsService acquisitionDetailsService) {
        this.acquisitionDetailsService = acquisitionDetailsService;
    }

    @Override
    public PaginatedResponse handle(GetSearchAcquisitionDetailsQuery query) {

        return this.acquisitionDetailsService.search(query.getPageable(),query.getFilter());
    }
}
