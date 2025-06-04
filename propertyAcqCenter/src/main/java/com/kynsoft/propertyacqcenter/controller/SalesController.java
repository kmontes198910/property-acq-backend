package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.sales.create.CreateSalesCommand;
import com.kynsoft.propertyacqcenter.application.command.sales.create.CreateSalesMessage;
import com.kynsoft.propertyacqcenter.application.command.sales.create.CreateSalesRequest;
import com.kynsoft.propertyacqcenter.application.command.sales.delete.DeleteSalesCommand;
import com.kynsoft.propertyacqcenter.application.command.sales.delete.DeleteSalesMessage;
import com.kynsoft.propertyacqcenter.application.command.sales.update.UpdateSalesCommand;
import com.kynsoft.propertyacqcenter.application.command.sales.update.UpdateSalesMessage;
import com.kynsoft.propertyacqcenter.application.command.sales.update.UpdateSalesRequest;
import com.kynsoft.propertyacqcenter.application.query.sales.getById.GetByIdSalesQuery;
import com.kynsoft.propertyacqcenter.application.query.sales.getByPropertyId.FindBySalesByPropertyIdQuery;
import com.kynsoft.propertyacqcenter.application.query.sales.search.GetSearchSalesQuery;
import com.kynsoft.propertyacqcenter.application.response.SalesPropertyResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final IMediator mediator;

    public SalesController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateSalesRequest request) {
        CreateSalesCommand createCommand = CreateSalesCommand.fromRequest(request);
        CreateSalesMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateSalesRequest request) {

        UpdateSalesCommand command = UpdateSalesCommand.fromRequest(request, id);
        UpdateSalesMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteSalesCommand command = new DeleteSalesCommand(id);
        DeleteSalesMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdSalesQuery query = new GetByIdSalesQuery(id);
        SalesPropertyResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/property/{id}")
    public ResponseEntity<?> getByPropertyId(@PathVariable String id) {

        FindBySalesByPropertyIdQuery query = new FindBySalesByPropertyIdQuery(id);
        SalesPropertyResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchSalesQuery query = new GetSearchSalesQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
