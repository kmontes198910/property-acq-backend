package com.kynsof.evaluation.controller;

import com.kynsof.evaluation.application.command.evaluationExamenType.create.CreateEvaluationExamenTypeCommand;
import com.kynsof.evaluation.application.command.evaluationExamenType.create.CreateEvaluationExamenTypeMessage;
import com.kynsof.evaluation.application.command.evaluationExamenType.create.CreateEvaluationExamenTypeRequest;
import com.kynsof.evaluation.application.command.evaluationExamenType.delete.DeleteEvaluationExamenTypeCommand;
import com.kynsof.evaluation.application.command.evaluationExamenType.delete.DeleteEvaluationExamenTypeMessage;
import com.kynsof.evaluation.application.command.evaluationExamenType.update.UpdateEvaluationExamenTypeCommand;
import com.kynsof.evaluation.application.command.evaluationExamenType.update.UpdateEvaluationExamenTypeMessage;
import com.kynsof.evaluation.application.command.evaluationExamenType.update.UpdateEvaluationExamenTypeRequest;
import com.kynsof.evaluation.application.object.response.EvaluationExamenTypeResponse;
import com.kynsof.evaluation.application.query.evaluationExamenType.getById.FindEvaluationExamentTypeByIdQuery;
import com.kynsof.evaluation.application.query.evaluationExamenType.search.GetSearchEvaluationExamenTypeQuery;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluation-examen-type")
public class EvaluationExamenTypeController {

    private final IMediator mediator;

    public EvaluationExamenTypeController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateEvaluationExamenTypeRequest request) {
        CreateEvaluationExamenTypeCommand createCommand = CreateEvaluationExamenTypeCommand.fromRequest(request);
        CreateEvaluationExamenTypeMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindEvaluationExamentTypeByIdQuery query = new FindEvaluationExamentTypeByIdQuery(id);
        EvaluationExamenTypeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchEvaluationExamenTypeQuery query = new GetSearchEvaluationExamenTypeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateEvaluationExamenTypeRequest request) {

        UpdateEvaluationExamenTypeCommand command = UpdateEvaluationExamenTypeCommand.fromRequest(request, id);
        UpdateEvaluationExamenTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteEvaluationExamenTypeCommand command = new DeleteEvaluationExamenTypeCommand(id);
        DeleteEvaluationExamenTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}
