package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.legalEntity.create.CreateLegalEntityCommand;
import com.kynsoft.propertyacqcenter.application.command.legalEntity.create.CreateLegalEntityMessage;
import com.kynsoft.propertyacqcenter.application.command.legalEntity.create.CreateLegalEntityRequest;
import com.kynsoft.propertyacqcenter.application.command.legalEntity.delete.DeleteLegalEntityCommand;
import com.kynsoft.propertyacqcenter.application.command.legalEntity.delete.DeleteLegalEntityMessage;
import com.kynsoft.propertyacqcenter.application.command.legalEntity.update.UpdateLegalEntityCommand;
import com.kynsoft.propertyacqcenter.application.command.legalEntity.update.UpdateLegalEntityMessage;
import com.kynsoft.propertyacqcenter.application.command.legalEntity.update.UpdateLegalEntityRequest;
import com.kynsoft.propertyacqcenter.application.query.legalEntity.getById.GetByIdLegalEntityQuery;
import com.kynsoft.propertyacqcenter.application.query.legalEntity.search.GetSearchLegalEntityQuery;
import com.kynsoft.propertyacqcenter.application.response.LegalEntityFindByIdResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/legal-entity")
public class LegalEntityController {

    private final IMediator mediator;

    public LegalEntityController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateLegalEntityMessage> createAllergy(@RequestBody CreateLegalEntityRequest request) {
        CreateLegalEntityCommand createCommand = CreateLegalEntityCommand.fromRequest(request);
        CreateLegalEntityMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateLegalEntityRequest request) {

        UpdateLegalEntityCommand command = UpdateLegalEntityCommand.fromRequest(request, id);
        UpdateLegalEntityMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteLegalEntityCommand command = new DeleteLegalEntityCommand(id);
        DeleteLegalEntityMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdLegalEntityQuery query = new GetByIdLegalEntityQuery(id);
        LegalEntityFindByIdResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchLegalEntityQuery query = new GetSearchLegalEntityQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
