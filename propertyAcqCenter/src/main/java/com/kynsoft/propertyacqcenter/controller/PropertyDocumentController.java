package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.propertyDocument.create.CreatePropertyDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyDocument.create.CreatePropertyDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyDocument.create.CreatePropertyDocumentRequest;
import com.kynsoft.propertyacqcenter.application.command.propertyDocument.delete.DeletePropertyDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyDocument.delete.DeletePropertyDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyDocument.update.UpdatePropertyDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyDocument.update.UpdatePropertyDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyDocument.update.UpdatePropertyDocumentRequest;
import com.kynsoft.propertyacqcenter.application.query.propertyDocument.getById.GetByIdPropertyDocumentQuery;
import com.kynsoft.propertyacqcenter.application.query.propertyDocument.search.GetSearchPropertyDocumentQuery;
import com.kynsoft.propertyacqcenter.application.response.PropertyDocumentResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property-document")
public class PropertyDocumentController {

    private final IMediator mediator;

    public PropertyDocumentController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreatePropertyDocumentMessage> createAllergy(@RequestBody CreatePropertyDocumentRequest request) {
        CreatePropertyDocumentCommand createCommand = CreatePropertyDocumentCommand.fromRequest(request);
        CreatePropertyDocumentMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdatePropertyDocumentRequest request) {

        UpdatePropertyDocumentCommand command = UpdatePropertyDocumentCommand.fromRequest(request, id);
        UpdatePropertyDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeletePropertyDocumentCommand command = new DeletePropertyDocumentCommand(id);
        DeletePropertyDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdPropertyDocumentQuery query = new GetByIdPropertyDocumentQuery(id);
        PropertyDocumentResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPropertyDocumentQuery query = new GetSearchPropertyDocumentQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
