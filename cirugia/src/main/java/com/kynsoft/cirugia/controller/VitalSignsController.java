package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.vitalsigns.create.CreateVitalSignsCommand;
import com.kynsoft.cirugia.application.command.vitalsigns.create.CreateVitalSignsMessage;
import com.kynsoft.cirugia.application.command.vitalsigns.create.CreateVitalSignsRequest;
import com.kynsoft.cirugia.application.command.vitalsigns.update.UpdateVitalSignsCommand;
import com.kynsoft.cirugia.application.command.vitalsigns.update.UpdateVitalSignsMessage;
import com.kynsoft.cirugia.application.command.vitalsigns.update.UpdateVitalSignsRequest;
import com.kynsoft.cirugia.application.command.vitalsigns.delete.DeleteVitalSignsCommand;
import com.kynsoft.cirugia.application.command.vitalsigns.delete.DeleteVitalSignsMessage;
import com.kynsoft.cirugia.application.query.vitalsigns.getbyid.GetVitalSignsByIdQuery;
import com.kynsoft.cirugia.application.query.vitalsigns.getbyid.VitalSignsResponse;
import com.kynsoft.cirugia.application.query.vitalsigns.search.SearchVitalSignsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/vital-signs")
@RequiredArgsConstructor
public class VitalSignsController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        VitalSignsResponse response = mediator.send(new GetVitalSignsByIdQuery(id));
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateVitalSignsRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        CreateVitalSignsCommand command = CreateVitalSignsCommand.fromRequest(request, UUID.fromString(userId));

        CreateVitalSignsMessage response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, 
                                   @RequestBody UpdateVitalSignsRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        UpdateVitalSignsCommand command = UpdateVitalSignsCommand.fromRequest(request, id, UUID.fromString(userId));
        UpdateVitalSignsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        DeleteVitalSignsMessage response = mediator.send(new DeleteVitalSignsCommand(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        SearchVitalSignsQuery query = new SearchVitalSignsQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}