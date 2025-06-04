package com.kynsoft.propertyacqcenter.application.query.sales.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.SalesPropertyResponse;
import com.kynsoft.propertyacqcenter.domain.services.ISalesPropertyService;
import org.springframework.stereotype.Component;

@Component
public class FindBySalesByPropertyIdQueryHandler implements IQueryHandler<FindBySalesByPropertyIdQuery, SalesPropertyResponse> {

    private final ISalesPropertyService salesPropertyService;

    public FindBySalesByPropertyIdQueryHandler(ISalesPropertyService salesPropertyService) {
        this.salesPropertyService = salesPropertyService;
    }

    @Override
    public SalesPropertyResponse handle(FindBySalesByPropertyIdQuery query) {
        return new SalesPropertyResponse(this.salesPropertyService.findByPropertyId(query.getId()));
    }
}
