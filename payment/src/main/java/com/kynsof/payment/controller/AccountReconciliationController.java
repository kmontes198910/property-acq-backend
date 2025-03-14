package com.kynsof.payment.controller;

import com.kynsof.payment.application.command.accountReconciliation.create.CreateAccountReconciliationCommand;
import com.kynsof.payment.application.command.accountReconciliation.create.CreateAccountReconciliationMessage;
import com.kynsof.payment.application.command.accountReconciliation.create.CreateAccountReconciliationRequest;
import com.kynsof.payment.application.command.accountReconciliation.delete.DeleteAccountReconciliationCommand;
import com.kynsof.payment.application.command.accountReconciliation.delete.DeleteAccountReconciliationMessage;
import com.kynsof.payment.application.command.accountReconciliation.update.UpdateAccountReconciliationCommand;
import com.kynsof.payment.application.command.accountReconciliation.update.UpdateAccountReconciliationMessage;
import com.kynsof.payment.application.command.accountReconciliation.update.UpdateAccountReconciliationRequest;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.payment.application.query.accountReconciliation.getById.FindByIdAccountReconciliationQuery;
import com.kynsof.payment.application.query.accountReconciliation.search.AccountReconciliationResponse;
import com.kynsof.payment.application.query.accountReconciliation.search.GetSearchAccountReconciliationQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/account-reconciliation")
public class AccountReconciliationController {

    private final IMediator mediator;

    public AccountReconciliationController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateAccountReconciliationRequest request) {
        CreateAccountReconciliationCommand createCommand = CreateAccountReconciliationCommand.fromRequest(request);
        CreateAccountReconciliationMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindByIdAccountReconciliationQuery query = new FindByIdAccountReconciliationQuery(id);
        AccountReconciliationResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchAccountReconciliationQuery query = new GetSearchAccountReconciliationQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateAccountReconciliationRequest request) {

        UpdateAccountReconciliationCommand command = UpdateAccountReconciliationCommand.fromRequest(request, id);
        UpdateAccountReconciliationMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteAccountReconciliationCommand command = new DeleteAccountReconciliationCommand(id);
        DeleteAccountReconciliationMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}
