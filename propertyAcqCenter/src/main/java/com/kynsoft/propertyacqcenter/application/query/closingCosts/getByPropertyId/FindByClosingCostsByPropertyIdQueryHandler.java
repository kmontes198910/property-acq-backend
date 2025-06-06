package com.kynsoft.propertyacqcenter.application.query.closingCosts.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.ClosingCostsResponse;
import com.kynsoft.propertyacqcenter.domain.services.IClosingCostsService;
import org.springframework.stereotype.Component;

@Component
public class FindByClosingCostsByPropertyIdQueryHandler implements IQueryHandler<FindByClosingCostsByPropertyIdQuery, ClosingCostsResponse> {

    private final IClosingCostsService closingCostsService;

    public FindByClosingCostsByPropertyIdQueryHandler(IClosingCostsService closingCostsService) {
        this.closingCostsService = closingCostsService;
    }

    @Override
    public ClosingCostsResponse handle(FindByClosingCostsByPropertyIdQuery query) {
        return new ClosingCostsResponse(this.closingCostsService.findByPropertyId(query.getId()));
    }
}
