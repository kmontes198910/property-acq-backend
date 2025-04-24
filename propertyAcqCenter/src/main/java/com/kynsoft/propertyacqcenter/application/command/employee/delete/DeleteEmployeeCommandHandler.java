package com.kynsoft.propertyacqcenter.application.command.employee.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.IEmployeeService;
import org.springframework.stereotype.Component;

@Component
public class DeleteEmployeeCommandHandler implements ICommandHandler<DeleteEmployeeCommand> {

    private final IEmployeeService employeeService;

    public DeleteEmployeeCommandHandler(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void handle(DeleteEmployeeCommand command) {
        this.employeeService.delete(command.getId());
    }
}
