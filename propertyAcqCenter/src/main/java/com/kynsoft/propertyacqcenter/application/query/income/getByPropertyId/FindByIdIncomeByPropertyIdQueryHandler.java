package com.kynsoft.propertyacqcenter.application.query.income.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.IncomeResponse;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdIncomeByPropertyIdQueryHandler implements IQueryHandler<FindByIdIncomeByPropertyIdQuery, IncomeResponse> {

    private final IIncomeService incomeService;

    public FindByIdIncomeByPropertyIdQueryHandler(IIncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Override
    public IncomeResponse handle(FindByIdIncomeByPropertyIdQuery query) {
        return new IncomeResponse(this.incomeService.findByPropertyId(query.getId()));
    }
}
