package com.kynsof.evaluation.controller;

import com.kynsof.evaluation.application.command.evaluation.create.CreateEvaluationCommand;
import com.kynsof.evaluation.application.command.evaluation.create.CreateEvaluationMessage;
import com.kynsof.evaluation.application.command.evaluation.create.CreateEvaluationRequest;
import com.kynsof.evaluation.application.command.evaluation.update.UpdateEvaluationCommand;
import com.kynsof.evaluation.application.command.evaluation.update.UpdateEvaluationRequest;
import com.kynsof.evaluation.application.command.evaluationExamenType.update.UpdateEvaluationExamenTypeMessage;
import com.kynsof.evaluation.application.command.evaluationPatient.create.CreateEvaluationPatientCommand;
import com.kynsof.evaluation.application.command.evaluationPatient.create.CreateEvaluationPatientMessage;
import com.kynsof.evaluation.application.command.evaluationPatient.create.CreateEvaluationPatientRequest;
import com.kynsof.evaluation.application.object.response.EvaluationResponse;
import com.kynsof.evaluation.application.query.evaluation.getById.GetByIdEvaluationQuery;
import com.kynsof.evaluation.application.query.evaluation.search.GetSearchEvaluationQuery;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/evaluation-patient-examen")
public class EvaluationPatientExamenController {

    private final IMediator mediator;

    public EvaluationPatientExamenController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateEvaluationPatientRequest request) {
        CreateEvaluationPatientCommand createCommand = CreateEvaluationPatientCommand.fromRequest(request);
        CreateEvaluationPatientMessage response = mediator.send(createCommand);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdEvaluationQuery query = new GetByIdEvaluationQuery(id);
        EvaluationResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchEvaluationQuery query = new GetSearchEvaluationQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateEvaluationRequest request) {

        UpdateEvaluationCommand command = UpdateEvaluationCommand.fromRequest(request, id);
        UpdateEvaluationExamenTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

//    @DeleteMapping(path = "/{id}")
//    public ResponseEntity<?> delete(@PathVariable UUID id) {
//
//        DeleteEvaluationExamenTypeCommand command = new DeleteEvaluationExamenTypeCommand(id);
//        DeleteEvaluationExamenTypeMessage response = mediator.send(command);
//        return ResponseEntity.ok(response);
//    }

}
