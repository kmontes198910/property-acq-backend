package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.exam.create.CreateExamCommand;
import com.kynsof.treatments.application.command.exam.create.CreateExamMessage;
import com.kynsof.treatments.application.command.exam.create.CreateExamRequest;
import com.kynsof.treatments.application.command.exam.delete.DeleteExamCommand;
import com.kynsof.treatments.application.command.exam.delete.ExamDeleteMessage;
import com.kynsof.treatments.application.command.exam.update.UpdateExamCommand;
import com.kynsof.treatments.application.command.exam.update.UpdateExamMessage;
import com.kynsof.treatments.application.command.exam.update.UpdateExamRequest;
import com.kynsof.treatments.application.query.exam.getbyid.ExamResponse;
import com.kynsof.treatments.application.query.exam.getbyid.FindByIdExamQuery;
import com.kynsof.treatments.application.query.exam.search.GetSearchExamQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/exam")
public class ExamController {

    private final IMediator mediator;

    public ExamController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateExamRequest request) {
        CreateExamCommand createCommand = CreateExamCommand.fromRequest(request);
        CreateExamMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExamResponse> getById(@PathVariable UUID id) {

        FindByIdExamQuery query = new FindByIdExamQuery(id);
        ExamResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchExamQuery query = new GetSearchExamQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateExamRequest request) {

        UpdateExamCommand command = UpdateExamCommand.fromRequest(request, id);
        UpdateExamMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteExamCommand query = new DeleteExamCommand(id);
        ExamDeleteMessage response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

}
