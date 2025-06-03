package com.kynsoft.propertyacqcenter.application.query.income.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.IncomeResponse;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdIncomeQueryHandler implements IQueryHandler<FindByIdIncomeQuery, IncomeResponse> {

    private final IIncomeService incomeService;

    public FindByIdIncomeQueryHandler(IIncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Override
    public IncomeResponse handle(FindByIdIncomeQuery query) {
        return new IncomeResponse(this.incomeService.findById(query.getId()));
    }
}
