package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.generalDocument.create.CreateGeneralDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.generalDocument.create.CreateGeneralDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.generalDocument.create.CreateGeneralDocumentRequest;
import com.kynsoft.propertyacqcenter.application.command.generalDocument.delete.DeleteGeneralDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.generalDocument.delete.DeleteGeneralDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.generalDocument.update.UpdateGeneralDocumentCommand;
import com.kynsoft.propertyacqcenter.application.command.generalDocument.update.UpdateGeneralDocumentMessage;
import com.kynsoft.propertyacqcenter.application.command.generalDocument.update.UpdateGeneralDocumentRequest;
import com.kynsoft.propertyacqcenter.application.query.generalDocument.getById.FindByIdGeneralDocumentQuery;
import com.kynsoft.propertyacqcenter.application.query.generalDocument.search.GetSearchGeneralDocumentQuery;
import com.kynsoft.propertyacqcenter.application.response.GeneralDocumentResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adquisition-document")
public class GeneralDocumentController {

    private final IMediator mediator;

    public GeneralDocumentController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateGeneralDocumentMessage> create(@RequestBody CreateGeneralDocumentRequest request) {
        CreateGeneralDocumentCommand createCommand = CreateGeneralDocumentCommand.fromRequest(request);
        CreateGeneralDocumentMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateGeneralDocumentRequest request) {

        UpdateGeneralDocumentCommand command = UpdateGeneralDocumentCommand.fromRequest(request, id);
        UpdateGeneralDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteGeneralDocumentCommand command = new DeleteGeneralDocumentCommand(id);
        DeleteGeneralDocumentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindByIdGeneralDocumentQuery query = new FindByIdGeneralDocumentQuery(id);
        GeneralDocumentResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchGeneralDocumentQuery query = new GetSearchGeneralDocumentQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
