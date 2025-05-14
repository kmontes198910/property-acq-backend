package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.ownerDocument.create.CreateOwnerDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.ownerDocument.create.CreateOwnerDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.ownerDocument.create.CreateOwnerDocumentRequest;
import com.kynsoft.propertyacqcenter.application.command.ownerDocument.delete.DeleteOwnerDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.ownerDocument.delete.DeleteOwnerDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.ownerDocument.update.UpdateOwnerDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.ownerDocument.update.UpdateOwnerDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.ownerDocument.update.UpdateOwnerDocumentRequest;
import com.kynsoft.propertyacqcenter.application.query.ownerDocument.getById.GetByIdOwnerDocumentQuery;
import com.kynsoft.propertyacqcenter.application.query.ownerDocument.search.GetSearchOwnerDocumentQuery;
import com.kynsoft.propertyacqcenter.application.response.OwnerDocumentResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner-document")
public class OwnerDocumentController {

    private final IMediator mediator;

    public OwnerDocumentController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateOwnerDocumentMessage> createAllergy(@RequestBody CreateOwnerDocumentRequest request) {
        CreateOwnerDocumentCommand createCommand = CreateOwnerDocumentCommand.fromRequest(request);
        CreateOwnerDocumentMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateOwnerDocumentRequest request) {

        UpdateOwnerDocumentCommand command = UpdateOwnerDocumentCommand.fromRequest(request, id);
        UpdateOwnerDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteOwnerDocumentCommand command = new DeleteOwnerDocumentCommand(id);
        DeleteOwnerDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdOwnerDocumentQuery query = new GetByIdOwnerDocumentQuery(id);
        OwnerDocumentResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchOwnerDocumentQuery query = new GetSearchOwnerDocumentQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
