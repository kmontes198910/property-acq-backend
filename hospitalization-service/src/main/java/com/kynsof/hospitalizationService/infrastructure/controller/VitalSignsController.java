package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.vitalSigns.create.CreateVitalSignsCommand;
import com.kynsof.hospitalizationService.application.command.vitalSigns.create.CreateVitalSignsMessage;
import com.kynsof.hospitalizationService.application.command.vitalSigns.create.CreateVitalSignsRequest;
import com.kynsof.hospitalizationService.application.command.vitalSigns.delete.DeleteVitalSignsCommand;
import com.kynsof.hospitalizationService.application.command.vitalSigns.delete.DeleteVitalSignsMessage;
import com.kynsof.hospitalizationService.application.command.vitalSigns.update.UpdateVitalSignsCommand;
import com.kynsof.hospitalizationService.application.command.vitalSigns.update.UpdateVitalSignsMessage;
import com.kynsof.hospitalizationService.application.command.vitalSigns.update.UpdateVitalSignsRequest;
import com.kynsof.hospitalizationService.application.query.emergencyCase.search.GetSearchEmergencyCaseQuery;
import com.kynsof.hospitalizationService.application.query.vitalSigns.getById.GetByIdVitalSignsQuery;
import com.kynsof.hospitalizationService.application.query.vitalSigns.search.GetSearchVitalSignsQuery;
import com.kynsof.hospitalizationService.application.response.VitalSignsResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vital-signs")
public class VitalSignsController {

    private final IMediator mediator;

    public VitalSignsController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateVitalSignsMessage> createAllergy(@RequestBody CreateVitalSignsRequest request) {
        CreateVitalSignsCommand createCommand = CreateVitalSignsCommand.fromRequest(request);
        CreateVitalSignsMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateVitalSignsRequest request) {

        UpdateVitalSignsCommand command = UpdateVitalSignsCommand.fromRequest(request, id);
        UpdateVitalSignsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteVitalSignsCommand command = new DeleteVitalSignsCommand(id);
        DeleteVitalSignsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdVitalSignsQuery query = new GetByIdVitalSignsQuery(id);
        VitalSignsResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchVitalSignsQuery query = new GetSearchVitalSignsQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
