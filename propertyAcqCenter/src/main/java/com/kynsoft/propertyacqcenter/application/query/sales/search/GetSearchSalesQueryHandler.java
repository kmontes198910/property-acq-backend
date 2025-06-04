package com.kynsoft.propertyacqcenter.application.query.sales.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.ISalesPropertyService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchSalesQueryHandler implements IQueryHandler<GetSearchSalesQuery, PaginatedResponse>{

    private final ISalesPropertyService salesPropertyService;

    public GetSearchSalesQueryHandler(ISalesPropertyService salesPropertyService) {
        this.salesPropertyService = salesPropertyService;
    }

    @Override
    public PaginatedResponse handle(GetSearchSalesQuery query) {

        return this.salesPropertyService.search(query.getPageable(),query.getFilter());
    }
}
