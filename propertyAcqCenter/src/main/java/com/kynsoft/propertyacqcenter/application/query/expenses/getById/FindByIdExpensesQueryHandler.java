package com.kynsoft.propertyacqcenter.application.query.expenses.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.ExpensesResponse;
import com.kynsoft.propertyacqcenter.domain.services.IExpensesService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdExpensesQueryHandler implements IQueryHandler<FindByIdExpensesQuery, ExpensesResponse> {

    private final IExpensesService expensesService;

    public FindByIdExpensesQueryHandler(IExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @Override
    public ExpensesResponse handle(FindByIdExpensesQuery query) {
        return new ExpensesResponse(this.expensesService.findById(query.getId()));
    }
}
