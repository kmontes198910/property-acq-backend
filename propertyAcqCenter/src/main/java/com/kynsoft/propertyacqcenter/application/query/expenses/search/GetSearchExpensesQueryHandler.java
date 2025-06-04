package com.kynsoft.propertyacqcenter.application.query.expenses.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IExpensesService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchExpensesQueryHandler implements IQueryHandler<GetSearchExpensesQuery, PaginatedResponse> {

    private final IExpensesService expensesService;

    public GetSearchExpensesQueryHandler(IExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @Override
    public PaginatedResponse handle(GetSearchExpensesQuery query) {
        return this.expensesService.search(query.getPageable(), query.getFilter());
    }
}
