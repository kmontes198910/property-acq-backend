package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.bankDocument.create.CreateBankDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.bankDocument.create.CreateBankDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.bankDocument.create.CreateBankDocumentRequest;
import com.kynsoft.propertyacqcenter.application.command.bankDocument.delete.DeleteBankDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.bankDocument.delete.DeleteBankDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.bankDocument.update.UpdateBankDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.bankDocument.update.UpdateBankDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.bankDocument.update.UpdateBankDocumentRequest;
import com.kynsoft.propertyacqcenter.application.query.bankDocument.getById.GetByIdBankDocumentQuery;
import com.kynsoft.propertyacqcenter.application.query.bankDocument.search.GetSearchBankDocumentQuery;
import com.kynsoft.propertyacqcenter.application.response.BankDocumentResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank-document")
public class BankDocumentController {

    private final IMediator mediator;

    public BankDocumentController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateBankDocumentMessage> create(@RequestBody CreateBankDocumentRequest request) {
        CreateBankDocumentCommand createCommand = CreateBankDocumentCommand.fromRequest(request);
        CreateBankDocumentMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateBankDocumentRequest request) {

        UpdateBankDocumentCommand command = UpdateBankDocumentCommand.fromRequest(request, id);
        UpdateBankDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteBankDocumentCommand command = new DeleteBankDocumentCommand(id);
        DeleteBankDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdBankDocumentQuery query = new GetByIdBankDocumentQuery(id);
        BankDocumentResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchBankDocumentQuery query = new GetSearchBankDocumentQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
