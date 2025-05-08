package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.recoverybed.create.CreateRecoveryBedCommand;
import com.kynsoft.cirugia.application.command.recoverybed.create.CreateRecoveryBedMessage;
import com.kynsoft.cirugia.application.command.recoverybed.create.CreateRecoveryBedRequest;
import com.kynsoft.cirugia.application.command.recoverybed.update.UpdateRecoveryBedCommand;
import com.kynsoft.cirugia.application.command.recoverybed.update.UpdateRecoveryBedMessage;
import com.kynsoft.cirugia.application.command.recoverybed.update.UpdateRecoveryBedRequest;
import com.kynsoft.cirugia.application.command.recoverybed.delete.DeleteRecoveryBedCommand;
import com.kynsoft.cirugia.application.command.recoverybed.changestatus.ChangeRecoveryBedStatusCommand;
import com.kynsoft.cirugia.application.query.recoverybed.getbyid.GetRecoveryBedByIdQuery;

import com.kynsoft.cirugia.application.query.recoverybed.search.SearchRecoveryBedsQuery;
import com.kynsoft.cirugia.application.query.recoverybed.getbyid.RecoveryBedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/recovery-beds")
@RequiredArgsConstructor
public class RecoveryBedController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        RecoveryBedResponse response = mediator.send(new GetRecoveryBedByIdQuery(id));
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateRecoveryBedRequest request,
                                                      @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
                                                      @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        CreateRecoveryBedCommand command = CreateRecoveryBedCommand.fromRequest(request, userId);
        CreateRecoveryBedMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateRecoveryBedRequest request,
                                                      @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
                                                      @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        UpdateRecoveryBedCommand command = UpdateRecoveryBedCommand.fromRequest(request, id, userId);
        UpdateRecoveryBedMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @PathVariable String status) {
        ChangeRecoveryBedStatusCommand command = new ChangeRecoveryBedStatusCommand(id, status);
        mediator.send(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        mediator.send(new DeleteRecoveryBedCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        SearchRecoveryBedsQuery query = new SearchRecoveryBedsQuery(request);
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}