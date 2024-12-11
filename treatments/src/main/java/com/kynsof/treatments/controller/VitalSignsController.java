package com.kynsof.treatments.controller;


import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.vitalsigns.create.CreateVitalSignsCommand;
import com.kynsof.treatments.application.command.vitalsigns.create.CreateVitalSignsMessage;
import com.kynsof.treatments.application.command.vitalsigns.create.CreateVitalSignsRequest;
import com.kynsof.treatments.application.command.vitalsigns.delete.DeleteVitalSignsCommand;
import com.kynsof.treatments.application.command.vitalsigns.delete.DeleteVitalSignsMessage;
import com.kynsof.treatments.application.command.vitalsigns.update.UpdateVitalSignsCommand;
import com.kynsof.treatments.application.command.vitalsigns.update.UpdateVitalSignsMessage;
import com.kynsof.treatments.application.command.vitalsigns.update.UpdateVitalSignsRequest;
import com.kynsof.treatments.application.query.vitalsigns.getById.FindByIdVitalSignsQuery;
import com.kynsof.treatments.application.query.vitalsigns.search.GetSearchVitalSignsQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vital-signs")
public class VitalSignsController {

    private final IMediator mediator;

    public VitalSignsController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateVitalSignsRequest request) {
        CreateVitalSignsCommand command = CreateVitalSignsCommand.fromRequest(request);
        CreateVitalSignsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateVitalSignsRequest request) {
        UpdateVitalSignsCommand command = UpdateVitalSignsCommand.fromRequest(id, request);
        UpdateVitalSignsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        FindByIdVitalSignsQuery query = new FindByIdVitalSignsQuery(id);
        var response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchVitalSignsQuery query = new GetSearchVitalSignsQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        DeleteVitalSignsCommand command = new DeleteVitalSignsCommand(id);
        DeleteVitalSignsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }
}