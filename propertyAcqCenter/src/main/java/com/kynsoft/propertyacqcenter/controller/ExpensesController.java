package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.expenses.create.CreateExpensesCommand;
import com.kynsoft.propertyacqcenter.application.command.expenses.create.CreateExpensesMessage;
import com.kynsoft.propertyacqcenter.application.command.expenses.create.CreateExpensesRequest;
import com.kynsoft.propertyacqcenter.application.command.expenses.delete.DeleteExpensesCommand;
import com.kynsoft.propertyacqcenter.application.command.expenses.delete.DeleteExpensesMessage;
import com.kynsoft.propertyacqcenter.application.command.expenses.update.UpdateExpensesCommand;
import com.kynsoft.propertyacqcenter.application.command.expenses.update.UpdateExpensesMessage;
import com.kynsoft.propertyacqcenter.application.command.expenses.update.UpdateExpensesRequest;
import com.kynsoft.propertyacqcenter.application.query.expenses.getById.FindByIdExpensesQuery;
import com.kynsoft.propertyacqcenter.application.query.expenses.getByPropertyId.FindByIdExpensesByPropertyIdQuery;
import com.kynsoft.propertyacqcenter.application.query.expenses.search.GetSearchExpensesQuery;
import com.kynsoft.propertyacqcenter.application.response.ExpensesResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesController {

    private final IMediator mediator;

    public ExpensesController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateExpensesRequest request) {
        CreateExpensesCommand createCommand = CreateExpensesCommand.fromRequest(request);
        CreateExpensesMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateExpensesRequest request) {

        UpdateExpensesCommand command = UpdateExpensesCommand.fromRequest(request, id);
        UpdateExpensesMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteExpensesCommand command = new DeleteExpensesCommand(id);
        DeleteExpensesMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindByIdExpensesQuery query = new FindByIdExpensesQuery(id);
        ExpensesResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/property/{id}")
    public ResponseEntity<?> getByPropertyId(@PathVariable String id) {

        FindByIdExpensesByPropertyIdQuery query = new FindByIdExpensesByPropertyIdQuery(id);
        ExpensesResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchExpensesQuery query = new GetSearchExpensesQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
