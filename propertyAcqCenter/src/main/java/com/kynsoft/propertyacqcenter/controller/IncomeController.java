package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.income.create.CreateIncomeCommand;
import com.kynsoft.propertyacqcenter.application.command.income.create.CreateIncomeMessage;
import com.kynsoft.propertyacqcenter.application.command.income.create.CreateIncomeRequest;
import com.kynsoft.propertyacqcenter.application.command.income.delete.DeleteIncomeCommand;
import com.kynsoft.propertyacqcenter.application.command.income.delete.DeleteIncomeMessage;
import com.kynsoft.propertyacqcenter.application.command.income.update.UpdateIncomeCommand;
import com.kynsoft.propertyacqcenter.application.command.income.update.UpdateIncomeMessage;
import com.kynsoft.propertyacqcenter.application.command.income.update.UpdateIncomeRequest;
import com.kynsoft.propertyacqcenter.application.query.income.getById.FindByIdIncomeQuery;
import com.kynsoft.propertyacqcenter.application.query.income.getByPropertyId.FindByIdIncomeByPropertyIdQuery;
import com.kynsoft.propertyacqcenter.application.query.income.search.GetSearchIncomeQuery;
import com.kynsoft.propertyacqcenter.application.response.IncomeResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/income")
public class IncomeController {

    private final IMediator mediator;

    public IncomeController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateIncomeRequest request) {
        CreateIncomeCommand createCommand = CreateIncomeCommand.fromRequest(request);
        CreateIncomeMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateIncomeRequest request) {

        UpdateIncomeCommand command = UpdateIncomeCommand.fromRequest(request, id);
        UpdateIncomeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteIncomeCommand command = new DeleteIncomeCommand(id);
        DeleteIncomeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindByIdIncomeQuery query = new FindByIdIncomeQuery(id);
        IncomeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/property/{id}")
    public ResponseEntity<?> getByPropertyId(@PathVariable String id) {

        FindByIdIncomeByPropertyIdQuery query = new FindByIdIncomeByPropertyIdQuery(id);
        IncomeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchIncomeQuery query = new GetSearchIncomeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
