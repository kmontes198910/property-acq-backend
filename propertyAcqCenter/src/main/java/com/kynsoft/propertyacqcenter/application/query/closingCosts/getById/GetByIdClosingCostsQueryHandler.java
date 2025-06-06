package com.kynsoft.propertyacqcenter.application.query.closingCosts.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.ClosingCostsResponse;
import com.kynsoft.propertyacqcenter.domain.services.IClosingCostsService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdClosingCostsQueryHandler implements IQueryHandler<GetByIdClosingCostsQuery, ClosingCostsResponse> {

    private final IClosingCostsService closingCostsService;

    public GetByIdClosingCostsQueryHandler(IClosingCostsService closingCostsService) {
        this.closingCostsService = closingCostsService;
    }

    @Override
    public ClosingCostsResponse handle(GetByIdClosingCostsQuery query) {
        return new ClosingCostsResponse(this.closingCostsService.findById(query.getId()));
    }
}
