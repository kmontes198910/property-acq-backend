package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.create.CreatePropertyUploadDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.create.CreatePropertyUploadDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.create.CreatePropertyUploadDocumentRequest;
import com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.delete.DeletePropertyUploadDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.delete.DeletePropertyUploadDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.update.UpdatePropertyUploadDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.update.UpdatePropertyUploadDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyUploadDocument.update.UpdatePropertyUploadDocumentRequest;
import com.kynsoft.propertyacqcenter.application.query.propertyUploadDocument.getById.GetByIdPropertyUploadDocumentQuery;
import com.kynsoft.propertyacqcenter.application.query.propertyUploadDocument.search.GetSearchPropertyUploadDocumentQuery;
import com.kynsoft.propertyacqcenter.application.response.PropertyUploadDocumentResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property-upload-document")
public class PropertyUploadDocumentController {

    private final IMediator mediator;

    public PropertyUploadDocumentController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreatePropertyUploadDocumentRequest request) {
        CreatePropertyUploadDocumentCommand createCommand = CreatePropertyUploadDocumentCommand.fromRequest(request);
        CreatePropertyUploadDocumentMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdatePropertyUploadDocumentRequest request) {

        UpdatePropertyUploadDocumentCommand command = UpdatePropertyUploadDocumentCommand.fromRequest(request, id);
        UpdatePropertyUploadDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeletePropertyUploadDocumentCommand command = new DeletePropertyUploadDocumentCommand(id);
        DeletePropertyUploadDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdPropertyUploadDocumentQuery query = new GetByIdPropertyUploadDocumentQuery(id);
        PropertyUploadDocumentResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPropertyUploadDocumentQuery query = new GetSearchPropertyUploadDocumentQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
