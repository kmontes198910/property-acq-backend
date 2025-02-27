package com.kynsof.payment.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.payment.application.command.billing.create.CreateBillingCommand;
import com.kynsof.payment.application.command.billing.create.CreateBillingMessage;
import com.kynsof.payment.application.command.billing.create.CreateBillingRequest;
import com.kynsof.payment.application.command.billing.createall.CreateAllBillingCommand;
import com.kynsof.payment.application.command.billing.createall.CreateAllBillingMessage;
import com.kynsof.payment.application.command.billing.createall.CreateBillingBulkRequest;
import com.kynsof.payment.application.command.billing.delete.BillingDeleteMessage;
import com.kynsof.payment.application.command.billing.delete.DeleteBillingCommand;
import com.kynsof.payment.application.command.billing.update.UpdateBillingCommand;
import com.kynsof.payment.application.command.billing.update.UpdateBillingMessage;
import com.kynsof.payment.application.command.billing.update.UpdateBillingRequest;
import com.kynsof.payment.application.query.billing.getbyid.BillingResponse;
import com.kynsof.payment.application.query.billing.getbyid.FindByIdBillingQuery;
import com.kynsof.payment.application.query.billing.search.GetSearchBillingQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/billing")
public class BillingController {

    private final IMediator mediator;

    public BillingController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateBillingRequest request) {
        CreateBillingCommand createCommand = CreateBillingCommand.fromRequest(request);
        CreateBillingMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PostMapping("block")
    public ResponseEntity<?> createAll(@RequestBody CreateBillingBulkRequest payload) {
        CreateAllBillingCommand createCommand = new CreateAllBillingCommand(
                payload.getClientId(),
                payload.getBusinessId(), 
                payload.isProforma(), 
                payload.getBillingPartialRequests()
        );
        CreateAllBillingMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindByIdBillingQuery query = new FindByIdBillingQuery(id);
        BillingResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchBillingQuery query = new GetSearchBillingQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateBillingRequest request) {

        UpdateBillingCommand command = UpdateBillingCommand.fromRequest(request, id);
        UpdateBillingMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteBillingCommand command = new DeleteBillingCommand(id);
        BillingDeleteMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}
