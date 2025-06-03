package com.kynsoft.propertyacqcenter.application.query.income.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchIncomeQueryHandler implements IQueryHandler<GetSearchIncomeQuery, PaginatedResponse> {

    private final IIncomeService incomeService;

    public GetSearchIncomeQueryHandler(IIncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @Override
    public PaginatedResponse handle(GetSearchIncomeQuery query) {
        return this.incomeService.search(query.getPageable(), query.getFilter());
    }
}
