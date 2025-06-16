package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.documentType.create.CreateDocumentTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.documentType.create.CreateDocumentTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.documentType.create.CreateDocumentTypeRequest;
import com.kynsoft.propertyacqcenter.application.command.documentType.delete.DeleteDocumentTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.documentType.delete.DeleteDocumentTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.documentType.update.UpdateDocumentTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.documentType.update.UpdateDocumentTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.documentType.update.UpdateDocumentTypeRequest;
import com.kynsoft.propertyacqcenter.application.query.documentType.getById.FindByIdDocumentTypeQuery;
import com.kynsoft.propertyacqcenter.application.query.documentType.search.GetSearchDocumentTypeQuery;
import com.kynsoft.propertyacqcenter.application.response.DocumentTypeResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document-type")
public class DocumentTypeController {

    private final IMediator mediator;

    public DocumentTypeController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateDocumentTypeMessage> create(@RequestBody CreateDocumentTypeRequest request) {
        CreateDocumentTypeCommand createCommand = CreateDocumentTypeCommand.fromRequest(request);
        CreateDocumentTypeMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateDocumentTypeRequest request) {

        UpdateDocumentTypeCommand command = UpdateDocumentTypeCommand.fromRequest(request, id);
        UpdateDocumentTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteDocumentTypeCommand command = new DeleteDocumentTypeCommand(id);
        DeleteDocumentTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindByIdDocumentTypeQuery query = new FindByIdDocumentTypeQuery(id);
        DocumentTypeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchDocumentTypeQuery query = new GetSearchDocumentTypeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
