package com.kynsoft.propertyacqcenter.application.query.expenses.getByPropertyId;

import com.kynsoft.propertyacqcenter.application.query.income.getByPropertyId.*;
import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindByIdExpensesByPropertyIdQuery implements IQuery {

    private final String id;
}
