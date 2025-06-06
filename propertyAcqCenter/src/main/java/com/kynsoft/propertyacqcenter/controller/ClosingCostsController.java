package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.closingCosts.create.CreateClosingCostsCommand;
import com.kynsoft.propertyacqcenter.application.command.closingCosts.create.CreateClosingCostsMessage;
import com.kynsoft.propertyacqcenter.application.command.closingCosts.create.CreateClosingCostsRequest;
import com.kynsoft.propertyacqcenter.application.command.closingCosts.delete.DeleteClosingCostsCommand;
import com.kynsoft.propertyacqcenter.application.command.closingCosts.delete.DeleteClosingCostsMessage;
import com.kynsoft.propertyacqcenter.application.command.closingCosts.update.UpdateClosingCostsCommand;
import com.kynsoft.propertyacqcenter.application.command.closingCosts.update.UpdateClosingCostsMessage;
import com.kynsoft.propertyacqcenter.application.command.closingCosts.update.UpdateClosingCostsRequest;
import com.kynsoft.propertyacqcenter.application.query.closingCosts.getById.GetByIdClosingCostsQuery;
import com.kynsoft.propertyacqcenter.application.query.closingCosts.getByPropertyId.FindByClosingCostsByPropertyIdQuery;
import com.kynsoft.propertyacqcenter.application.query.closingCosts.search.GetSearchClosingCostsQuery;
import com.kynsoft.propertyacqcenter.application.response.ClosingCostsResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/closing-costs")
public class ClosingCostsController {

    private final IMediator mediator;

    public ClosingCostsController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateClosingCostsRequest request) {
        CreateClosingCostsCommand createCommand = CreateClosingCostsCommand.fromRequest(request);
        CreateClosingCostsMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateClosingCostsRequest request) {

        UpdateClosingCostsCommand command = UpdateClosingCostsCommand.fromRequest(request, id);
        UpdateClosingCostsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteClosingCostsCommand command = new DeleteClosingCostsCommand(id);
        DeleteClosingCostsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdClosingCostsQuery query = new GetByIdClosingCostsQuery(id);
        ClosingCostsResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/property/{id}")
    public ResponseEntity<?> getByPropertyId(@PathVariable String id) {

        FindByClosingCostsByPropertyIdQuery query = new FindByClosingCostsByPropertyIdQuery(id);
        ClosingCostsResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchClosingCostsQuery query = new GetSearchClosingCostsQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
