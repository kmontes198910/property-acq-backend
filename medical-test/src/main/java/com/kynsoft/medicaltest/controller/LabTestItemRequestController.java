package com.kynsoft.medicaltest.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.medicaltest.application.command.labTestItemRequest.create.CreateLabTestItemRequestCommand;
import com.kynsoft.medicaltest.application.command.labTestItemRequest.create.CreateLabTestItemRequestMessage;
import com.kynsoft.medicaltest.application.command.labTestItemRequest.create.CreateLabTestItemRequestRequest;
import com.kynsoft.medicaltest.application.command.labTestItemRequest.delete.DeleteLabTestItemRequestCommand;
import com.kynsoft.medicaltest.application.command.labTestItemRequest.delete.DeleteLabTestItemRequestMessage;
import com.kynsoft.medicaltest.application.command.labTestItemRequest.update.UpdateLabTestItemRequestCommand;
import com.kynsoft.medicaltest.application.command.labTestItemRequest.update.UpdateLabTestItemRequestMessage;
import com.kynsoft.medicaltest.application.command.labTestItemRequest.update.UpdateLabTestItemRequestRequest;
import com.kynsoft.medicaltest.application.query.labTestItemRequest.getbyid.GetLabTestItemRequestByIdQuery;
import com.kynsoft.medicaltest.application.query.labTestItemRequest.getbyid.LabTestItemRequestResponse;
import com.kynsoft.medicaltest.application.query.labTestItemRequest.search.SearchLabTestItemRequestQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/lab-tests-item-request")
@RequiredArgsConstructor
public class LabTestItemRequestController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        LabTestItemRequestResponse response = mediator.send(new GetLabTestItemRequestByIdQuery(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateLabTestItemRequestRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        CreateLabTestItemRequestCommand command = CreateLabTestItemRequestCommand.fromRequest(request, UUID.fromString(userId));

        CreateLabTestItemRequestMessage response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, 
                                   @RequestBody UpdateLabTestItemRequestRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        UpdateLabTestItemRequestCommand command = UpdateLabTestItemRequestCommand.fromRequest(request, id, UUID.fromString(userId));
        UpdateLabTestItemRequestMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        DeleteLabTestItemRequestMessage response = mediator.send(new DeleteLabTestItemRequestCommand(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        SearchLabTestItemRequestQuery query = new SearchLabTestItemRequestQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}
