package com.kynsoft.propertyacqcenter.application.query.sales.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.SalesPropertyResponse;
import com.kynsoft.propertyacqcenter.domain.services.ISalesPropertyService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdSalesQueryHandler implements IQueryHandler<GetByIdSalesQuery, SalesPropertyResponse>{

    private final ISalesPropertyService salesPropertyService;

    public GetByIdSalesQueryHandler(ISalesPropertyService salesPropertyService) {
        this.salesPropertyService = salesPropertyService;
    }

    @Override
    public SalesPropertyResponse handle(GetByIdSalesQuery query) {

        return new SalesPropertyResponse(this.salesPropertyService.findById(query.getId()));
    }
}
