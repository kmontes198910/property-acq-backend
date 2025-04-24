package com.kynsoft.propertyacqcenter.application.query.employee.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.EmployeeResponse;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdEmployeeQueryHandler implements IQueryHandler<FindByIdEmployeeQuery, EmployeeResponse> {

    private final IEmployeeService serviceImpl;

    public FindByIdEmployeeQueryHandler(IEmployeeService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public EmployeeResponse handle(FindByIdEmployeeQuery query) {
        return new EmployeeResponse(this.serviceImpl.findById(query.getId()));
    }
}
