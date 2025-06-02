package com.kynsoft.propertyacqcenter.application.query.employee.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchEmployeeQueryHandler implements IQueryHandler<GetSearchEmployeeQuery, PaginatedResponse> {

    private final IEmployeeService employeeService;

    public GetSearchEmployeeQueryHandler(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public PaginatedResponse handle(GetSearchEmployeeQuery query) {
        return this.employeeService.search(query.getPageable(), query.getFilter());
    }
}
