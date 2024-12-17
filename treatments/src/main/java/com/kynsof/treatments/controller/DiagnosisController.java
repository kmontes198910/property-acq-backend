package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.diagnosis.create.CreateDiagnosisCommand;
import com.kynsof.treatments.application.command.diagnosis.create.CreateDiagnosisMessage;
import com.kynsof.treatments.application.command.diagnosis.create.DiagnosisRequest;
import com.kynsof.treatments.application.command.diagnosis.delete.DiagnosisDeleteCommand;
import com.kynsof.treatments.application.command.diagnosis.delete.DiagnosisDeleteMessage;
import com.kynsof.treatments.application.command.diagnosis.update.UpdateDiagnosisCommand;
import com.kynsof.treatments.application.command.diagnosis.update.UpdateDiagnosisMessage;
import com.kynsof.treatments.application.command.diagnosis.update.UpdateDiagnosisRequest;
import com.kynsof.treatments.application.query.diagnosis.getbyid.DiagnosisResponse;
import com.kynsof.treatments.application.query.diagnosis.getbyid.FindByIdDiagnosisQuery;
import com.kynsof.treatments.application.query.diagnosis.getdiagnosisbyidexternalconsultation.FindDiagnosisByIdExternalConsultationQuery;
import com.kynsof.treatments.application.query.diagnosis.search.GetSearchDiagnosisQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/external-consultation/diagnosis")
public class DiagnosisController {

    private final IMediator mediator;

    public DiagnosisController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody DiagnosisRequest request) {
        CreateDiagnosisCommand createCommand = CreateDiagnosisCommand.fromRequest(request);
        CreateDiagnosisMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DiagnosisResponse> getById(@PathVariable UUID id) {

        FindByIdDiagnosisQuery query = new FindByIdDiagnosisQuery(id);
        DiagnosisResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/by-id-external-consultation/{id}")
    public ResponseEntity<PaginatedResponse> findDiagnosisByIdExternalConsultation(@PathVariable UUID id) {

        Pageable pageable = PageRequest.of(0, 1000);
        FindDiagnosisByIdExternalConsultationQuery query = new FindDiagnosisByIdExternalConsultationQuery(id, pageable);
        PaginatedResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,@RequestBody UpdateDiagnosisRequest request) {

        UpdateDiagnosisCommand command = UpdateDiagnosisCommand.fromRequest(id,request);
        UpdateDiagnosisMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchDiagnosisQuery query = new GetSearchDiagnosisQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DiagnosisDeleteCommand query = new DiagnosisDeleteCommand(id);
        DiagnosisDeleteMessage response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

}
