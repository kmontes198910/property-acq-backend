package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.diagnosis.create.CreateDiagnosisCommand;
import com.kynsof.hospitalizationService.application.command.diagnosis.create.CreateDiagnosisMessage;
import com.kynsof.hospitalizationService.application.command.diagnosis.create.CreateDiagnosisRequest;
import com.kynsof.hospitalizationService.application.command.diagnosis.delete.DeleteDiagnosisCommand;
import com.kynsof.hospitalizationService.application.command.diagnosis.delete.DeleteDiagnosisMessage;
import com.kynsof.hospitalizationService.application.command.diagnosis.update.UpdateDiagnosisCommand;
import com.kynsof.hospitalizationService.application.command.diagnosis.update.UpdateDiagnosisMessage;
import com.kynsof.hospitalizationService.application.command.diagnosis.update.UpdateDiagnosisRequest;
import com.kynsof.hospitalizationService.application.query.diagnosis.getById.GetByIdDiagnosisQuery;
import com.kynsof.hospitalizationService.application.query.diagnosis.search.GetSearchDiagnosisQuery;
import com.kynsof.hospitalizationService.application.response.DiagnosisResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diagnosis")
public class DiagnosisController {

    private final IMediator mediator;

    public DiagnosisController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateDiagnosisMessage> createAllergy(@RequestBody CreateDiagnosisRequest request) {
        CreateDiagnosisCommand createCommand = CreateDiagnosisCommand.fromRequest(request);
        CreateDiagnosisMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateDiagnosisRequest request) {

        UpdateDiagnosisCommand command = UpdateDiagnosisCommand.fromRequest(request, id);
        UpdateDiagnosisMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteDiagnosisCommand command = new DeleteDiagnosisCommand(id);
        DeleteDiagnosisMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdDiagnosisQuery query = new GetByIdDiagnosisQuery(id);
        DiagnosisResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchDiagnosisQuery query = new GetSearchDiagnosisQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
