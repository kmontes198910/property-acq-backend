package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.employee.create.CreateEmployeeCommand;
import com.kynsoft.propertyacqcenter.application.command.employee.create.CreateEmployeeMessage;
import com.kynsoft.propertyacqcenter.application.command.employee.create.CreateEmployeeRequest;
import com.kynsoft.propertyacqcenter.application.command.employee.delete.DeleteEmployeeCommand;
import com.kynsoft.propertyacqcenter.application.command.employee.delete.DeleteEmployeeMessage;
import com.kynsoft.propertyacqcenter.application.command.employee.update.UpdateEmployeeCommand;
import com.kynsoft.propertyacqcenter.application.command.employee.update.UpdateEmployeeMessage;
import com.kynsoft.propertyacqcenter.application.command.employee.update.UpdateEmployeeRequest;
import com.kynsoft.propertyacqcenter.application.query.employee.getById.FindByIdEmployeeQuery;
import com.kynsoft.propertyacqcenter.application.query.employee.search.GetSearchEmployeeQuery;
import com.kynsoft.propertyacqcenter.application.response.EmployeeResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private final IMediator mediator;

    public EmployeeController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> createEmployee(@RequestBody CreateEmployeeRequest request) {
        CreateEmployeeCommand command = CreateEmployeeCommand.fromRequest(request);
        CreateEmployeeMessage message = mediator.send(command);

        return ResponseEntity.ok(message);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id") UUID id, @RequestBody UpdateEmployeeRequest request) {
        UpdateEmployeeCommand command = UpdateEmployeeCommand.fromRequest(id, request);
        UpdateEmployeeMessage message = mediator.send(command);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") UUID id) {
        FindByIdEmployeeQuery query = new FindByIdEmployeeQuery(id);
        EmployeeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") UUID id) {
        DeleteEmployeeCommand command = new DeleteEmployeeCommand(id);
        DeleteEmployeeMessage message = mediator.send(command);

        return ResponseEntity.ok(message);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchEmployeeQuery query = new GetSearchEmployeeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);

        return ResponseEntity.ok(data);
    }
}
