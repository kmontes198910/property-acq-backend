package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.result.create.CreateResultCommand;
import com.kynsof.treatments.application.command.result.create.CreateResultMessage;
import com.kynsof.treatments.application.command.result.create.CreateResultRequest;
import com.kynsof.treatments.application.command.result.delete.DeleteResultCommand;
import com.kynsof.treatments.application.command.result.delete.DeleteResultRequest;
import com.kynsof.treatments.application.command.result.update.UpdateResultCommand;
import com.kynsof.treatments.application.command.result.update.UpdateResultMessage;
import com.kynsof.treatments.application.command.result.update.UpdateResultRequest;
import com.kynsof.treatments.application.query.result.GetResultsByExternalConsultation.GetResultsByExternalConsultationIdQuery;
import com.kynsof.treatments.application.query.result.GetResultsByExternalConsultation.ResultResponseByExternalConsult;
import com.kynsof.treatments.application.query.result.ResultResponse;
import com.kynsof.treatments.application.query.result.getById.GetResultByIdQuery;
import com.kynsof.treatments.application.query.result.search.GetSearchResultQuery;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/results")
@AllArgsConstructor
public class ResultController {

    private final IMediator mediator;

    @PostMapping
    public ResponseEntity<?> createResult(
            @RequestBody CreateResultRequest request,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Name") String username) {
        
        // Establecer los valores de usuario desde los headers
        request.setUploadedById(userId);
        request.setUploadedByUsername(username);
        
        CreateResultCommand command = CreateResultCommand.fromRequest(request);
        CreateResultMessage resultMessage = mediator.send(command);
        
        return ResponseEntity.ok(resultMessage);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateResult(
            @PathVariable UUID id,
            @RequestBody UpdateResultRequest request,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Name") String username) {
        
        request.setId(id);
        // Establecer los valores de usuario desde los headers
        request.setUploadedById(userId);
        request.setUploadedByUsername(username);
        
        UpdateResultCommand command = UpdateResultCommand.fromRequest(request);
        UpdateResultMessage resultMessage = mediator.send(command);
        
        return ResponseEntity.ok(resultMessage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResult(@PathVariable UUID id) {
        DeleteResultRequest request = new DeleteResultRequest(id);
        DeleteResultCommand command = DeleteResultCommand.fromRequest(request);
        mediator.send(command);
        
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse> getResultById(@PathVariable UUID id) {
        GetResultByIdQuery query = new GetResultByIdQuery(id);
        ResultResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/external-consultation/{externalConsultationId}")
    public ResponseEntity<?> getResultsByExternalConsultationId(
            @PathVariable UUID externalConsultationId) {
        
        GetResultsByExternalConsultationIdQuery query = new GetResultsByExternalConsultationIdQuery(externalConsultationId);
        ResultResponseByExternalConsult response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchResultQuery query = new GetSearchResultQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }
}