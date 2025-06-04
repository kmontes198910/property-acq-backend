package com.kynsoft.propertyacqcenter.application.query.expenses.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.ExpensesResponse;
import com.kynsoft.propertyacqcenter.domain.services.IExpensesService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdExpensesByPropertyIdQueryHandler implements IQueryHandler<FindByIdExpensesByPropertyIdQuery, ExpensesResponse> {

    private final IExpensesService expensesService;

    public FindByIdExpensesByPropertyIdQueryHandler(IExpensesService expensesService) {
        this.expensesService = expensesService;
    }

    @Override
    public ExpensesResponse handle(FindByIdExpensesByPropertyIdQuery query) {
        return new ExpensesResponse(this.expensesService.findByPropertyId(query.getId()));
    }
}
