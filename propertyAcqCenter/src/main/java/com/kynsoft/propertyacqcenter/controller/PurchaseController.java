package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.purchase.create.CreatePurchaseCommand;
import com.kynsoft.propertyacqcenter.application.command.purchase.create.CreatePurchaseMessage;
import com.kynsoft.propertyacqcenter.application.command.purchase.create.CreatePurchaseRequest;
import com.kynsoft.propertyacqcenter.application.command.purchase.delete.DeletePurchaseCommand;
import com.kynsoft.propertyacqcenter.application.command.purchase.delete.DeletePurchaseMessage;
import com.kynsoft.propertyacqcenter.application.command.purchase.update.UpdatePurchaseCommand;
import com.kynsoft.propertyacqcenter.application.command.purchase.update.UpdatePurchaseMessage;
import com.kynsoft.propertyacqcenter.application.command.purchase.update.UpdatePurchaseRequest;
import com.kynsoft.propertyacqcenter.application.query.purchase.getById.GetByIdPurchaseQuery;
import com.kynsoft.propertyacqcenter.application.query.purchase.getByPropertyId.FindByPurchaseByPropertyIdQuery;
import com.kynsoft.propertyacqcenter.application.query.purchase.search.GetSearchPurchaseQuery;
import com.kynsoft.propertyacqcenter.application.response.PurchaseResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final IMediator mediator;

    public PurchaseController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreatePurchaseRequest request) {
        CreatePurchaseCommand createCommand = CreatePurchaseCommand.fromRequest(request);
        CreatePurchaseMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdatePurchaseRequest request) {

        UpdatePurchaseCommand command = UpdatePurchaseCommand.fromRequest(request, id);
        UpdatePurchaseMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeletePurchaseCommand command = new DeletePurchaseCommand(id);
        DeletePurchaseMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdPurchaseQuery query = new GetByIdPurchaseQuery(id);
        PurchaseResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/property/{id}")
    public ResponseEntity<?> getByPropertyId(@PathVariable String id) {

        FindByPurchaseByPropertyIdQuery query = new FindByPurchaseByPropertyIdQuery(id);
        PurchaseResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPurchaseQuery query = new GetSearchPurchaseQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
