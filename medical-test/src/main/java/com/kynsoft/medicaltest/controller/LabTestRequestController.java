package com.kynsoft.medicaltest.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.medicaltest.application.command.labTestRequest.create.CreateLabTestRequestCommand;
import com.kynsoft.medicaltest.application.command.labTestRequest.create.CreateLabTestRequestMessage;
import com.kynsoft.medicaltest.application.command.labTestRequest.create.CreateLabTestRequestRequest;
import com.kynsoft.medicaltest.application.command.labTestRequest.delete.DeleteLabTestRequestCommand;
import com.kynsoft.medicaltest.application.command.labTestRequest.delete.DeleteLabTestRequestMessage;
import com.kynsoft.medicaltest.application.command.labTestRequest.update.UpdateLabTestRequestCommand;
import com.kynsoft.medicaltest.application.command.labTestRequest.update.UpdateLabTestRequestMessage;
import com.kynsoft.medicaltest.application.command.labTestRequest.update.UpdateLabTestRequestRequest;
import com.kynsoft.medicaltest.application.query.labTestRequest.getbyid.GetLabTestRequestByIdQuery;
import com.kynsoft.medicaltest.application.query.labTestRequest.getbyid.LabTestRequestResponse;
import com.kynsoft.medicaltest.application.query.labTestRequest.search.SearchLabTestRequestQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/lab-tests-request")
@RequiredArgsConstructor
public class LabTestRequestController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        LabTestRequestResponse response = mediator.send(new GetLabTestRequestByIdQuery(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateLabTestRequestRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        CreateLabTestRequestCommand command = CreateLabTestRequestCommand.fromRequest(request, UUID.fromString(userId));

        CreateLabTestRequestMessage response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, 
                                   @RequestBody UpdateLabTestRequestRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        UpdateLabTestRequestCommand command = UpdateLabTestRequestCommand.fromRequest(request, id, UUID.fromString(userId));
        UpdateLabTestRequestMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        DeleteLabTestRequestMessage response = mediator.send(new DeleteLabTestRequestCommand(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        SearchLabTestRequestQuery query = new SearchLabTestRequestQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}
