package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.optometryExam.create.CreateOptometryExamCommand;
import com.kynsof.treatments.application.command.optometryExam.create.CreateOptometryExamMessage;
import com.kynsof.treatments.application.command.optometryExam.create.CreateOptometryExamRequest;
import com.kynsof.treatments.application.command.optometryExam.delete.OptometryExamDeleteCommand;
import com.kynsof.treatments.application.command.optometryExam.delete.OptometryExamDeleteMessage;
import com.kynsof.treatments.application.command.optometryExam.update.UpdateOptometryExamCommand;
import com.kynsof.treatments.application.command.optometryExam.update.UpdateOptometryExamMessage;
import com.kynsof.treatments.application.command.optometryExam.update.UpdateOptometryExamRequest;
import com.kynsof.treatments.application.query.optometryExam.getById.FindByIdOptometryExamQuery;
import com.kynsof.treatments.application.query.optometryExam.getById.OptometryExamResponse;
import com.kynsof.treatments.application.query.optometryExam.getByPatientId.FindByPatientIdOptometryExamQuery;
import com.kynsof.treatments.application.query.optometryExam.search.GetSearchOptometryExamQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/optometry-exam")
public class OptometryExamController {

    private final IMediator mediator;

    public OptometryExamController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateOptometryExamRequest request) {
        CreateOptometryExamCommand createCommand = CreateOptometryExamCommand.fromRequest(request);
        CreateOptometryExamMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        FindByIdOptometryExamQuery query = new FindByIdOptometryExamQuery(id);
        OptometryExamResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/patient/{id}")
    public ResponseEntity<?> getByPatientId(@PathVariable UUID id) {
        FindByPatientIdOptometryExamQuery query = new FindByPatientIdOptometryExamQuery(id);
        OptometryExamResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchOptometryExamQuery query = new GetSearchOptometryExamQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);

        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateOptometryExamRequest request) {
        UpdateOptometryExamCommand command = UpdateOptometryExamCommand.fromRequest(request, id);
        UpdateOptometryExamMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        OptometryExamDeleteCommand query = new OptometryExamDeleteCommand(id);
        OptometryExamDeleteMessage response = mediator.send(query);

        return ResponseEntity.ok(response);
    }
}