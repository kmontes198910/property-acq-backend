package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.create.CreateOwnerShipLegalEntityCommand;
import com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.create.CreateOwnerShipLegalEntityMessage;
import com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.create.CreateOwnerShipLegalEntityRequest;
import com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.delete.DeleteOwnerShipLegalEntityCommand;
import com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.delete.DeleteOwnerShipLegalEntityMessage;
import com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.update.UpdateOwnerShipLegalEntityCommand;
import com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.update.UpdateOwnerShipLegalEntityMessage;
import com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.update.UpdateOwnerShipLegalEntityRequest;
import com.kynsoft.propertyacqcenter.application.query.ownerShipLegalEntity.getById.GetByIdOwnerShipLegalEntityQuery;
import com.kynsoft.propertyacqcenter.application.query.ownerShipLegalEntity.search.GetSearchOwnerShipLegalEntityQuery;
import com.kynsoft.propertyacqcenter.application.response.OwnerShipLegalEntityResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner-ship-legal-entity")
public class OwnerShipLegalEntityController {

    private final IMediator mediator;

    public OwnerShipLegalEntityController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateOwnerShipLegalEntityMessage> createAllergy(@RequestBody CreateOwnerShipLegalEntityRequest request) {
        CreateOwnerShipLegalEntityCommand createCommand = CreateOwnerShipLegalEntityCommand.fromRequest(request);
        CreateOwnerShipLegalEntityMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateOwnerShipLegalEntityRequest request) {

        UpdateOwnerShipLegalEntityCommand command = UpdateOwnerShipLegalEntityCommand.fromRequest(request, id);
        UpdateOwnerShipLegalEntityMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteOwnerShipLegalEntityCommand command = new DeleteOwnerShipLegalEntityCommand(id);
        DeleteOwnerShipLegalEntityMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdOwnerShipLegalEntityQuery query = new GetByIdOwnerShipLegalEntityQuery(id);
        OwnerShipLegalEntityResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchOwnerShipLegalEntityQuery query = new GetSearchOwnerShipLegalEntityQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
