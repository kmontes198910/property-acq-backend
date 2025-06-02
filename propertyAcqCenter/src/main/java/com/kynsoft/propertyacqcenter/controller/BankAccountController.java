package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.bankAccount.create.CreateBankAccountCommand;
import com.kynsoft.propertyacqcenter.application.command.bankAccount.create.CreateBankAccountMessage;
import com.kynsoft.propertyacqcenter.application.command.bankAccount.create.CreateBankAccountRequest;
import com.kynsoft.propertyacqcenter.application.command.bankAccount.delete.DeleteBankAccountCommand;
import com.kynsoft.propertyacqcenter.application.command.bankAccount.delete.DeleteBankAccountMessage;
import com.kynsoft.propertyacqcenter.application.command.bankAccount.update.UpdateBankAccountCommand;
import com.kynsoft.propertyacqcenter.application.command.bankAccount.update.UpdateBankAccountMessage;
import com.kynsoft.propertyacqcenter.application.command.bankAccount.update.UpdateBankAccountRequest;
import com.kynsoft.propertyacqcenter.application.query.BankAccount.getById.GetByIdBankAccountQuery;
import com.kynsoft.propertyacqcenter.application.query.BankAccount.search.GetSearchBankAccountQuery;
import com.kynsoft.propertyacqcenter.application.response.BankAccountFindByIdResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-account")
public class BankAccountController {

    private final IMediator mediator;

    public BankAccountController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateBankAccountMessage> createAllergy(@RequestBody CreateBankAccountRequest request) {
        CreateBankAccountCommand createCommand = CreateBankAccountCommand.fromRequest(request);
        CreateBankAccountMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateBankAccountRequest request) {

        UpdateBankAccountCommand command = UpdateBankAccountCommand.fromRequest(request, id);
        UpdateBankAccountMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteBankAccountCommand command = new DeleteBankAccountCommand(id);
        DeleteBankAccountMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdBankAccountQuery query = new GetByIdBankAccountQuery(id);
        BankAccountFindByIdResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchBankAccountQuery query = new GetSearchBankAccountQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
