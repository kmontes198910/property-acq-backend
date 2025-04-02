package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.ubication.create.CreateUbicationCommand;
import com.kynsof.hospitalizationService.application.command.ubication.create.CreateUbicationMessage;
import com.kynsof.hospitalizationService.application.command.ubication.create.CreateUbicationRequest;
import com.kynsof.hospitalizationService.application.command.ubication.delete.DeleteUbicationCommand;
import com.kynsof.hospitalizationService.application.command.ubication.delete.DeleteUbicationMessage;
import com.kynsof.hospitalizationService.application.command.ubication.update.UpdateUbicationCommand;
import com.kynsof.hospitalizationService.application.command.ubication.update.UpdateUbicationMessage;
import com.kynsof.hospitalizationService.application.command.ubication.update.UpdateUbicationRequest;
import com.kynsof.hospitalizationService.application.query.ubication.getById.GetByIdUbicationQuery;
import com.kynsof.hospitalizationService.application.query.ubication.search.GetSearchUbicationQuery;
import com.kynsof.hospitalizationService.application.response.UbicationResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ubication")
public class UbicationController {

    private final IMediator mediator;

    public UbicationController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateUbicationMessage> createAllergy(@RequestBody CreateUbicationRequest request) {
        CreateUbicationCommand createCommand = CreateUbicationCommand.fromRequest(request);
        CreateUbicationMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateUbicationRequest request) {

        UpdateUbicationCommand command = UpdateUbicationCommand.fromRequest(request, id);
        UpdateUbicationMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteUbicationCommand command = new DeleteUbicationCommand(id);
        DeleteUbicationMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdUbicationQuery query = new GetByIdUbicationQuery(id);
        UbicationResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchUbicationQuery query = new GetSearchUbicationQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
