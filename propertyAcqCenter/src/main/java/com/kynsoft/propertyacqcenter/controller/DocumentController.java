package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.document.create.CreateDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.document.create.CreateDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.document.create.CreateDocumentRequest;
import com.kynsoft.propertyacqcenter.application.command.document.delete.DeleteDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.document.delete.DeleteDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.document.update.UpdateDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.document.update.UpdateDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.document.update.UpdateDocumentRequest;
import com.kynsoft.propertyacqcenter.application.query.document.getById.GetByIdDocumentQuery;
import com.kynsoft.propertyacqcenter.application.query.document.search.GetSearchDocumentQuery;
import com.kynsoft.propertyacqcenter.application.response.DocumentFindByIdResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
public class DocumentController {

    private final IMediator mediator;

    public DocumentController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateDocumentMessage> createAllergy(@RequestBody CreateDocumentRequest request) {
        CreateDocumentCommand createCommand = CreateDocumentCommand.fromRequest(request);
        CreateDocumentMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateDocumentRequest request) {

        UpdateDocumentCommand command = UpdateDocumentCommand.fromRequest(request, id);
        UpdateDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteDocumentCommand command = new DeleteDocumentCommand(id);
        DeleteDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdDocumentQuery query = new GetByIdDocumentQuery(id);
        DocumentFindByIdResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchDocumentQuery query = new GetSearchDocumentQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
