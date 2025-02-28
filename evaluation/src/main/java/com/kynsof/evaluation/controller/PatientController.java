package com.kynsof.evaluation.controller;

import com.kynsof.evaluation.application.command.patients.create.CreatePatientMessage;
import com.kynsof.evaluation.application.command.patients.create.CreatePatientsCommand;
import com.kynsof.evaluation.application.command.patients.create.CreatePatientsRequest;
import com.kynsof.evaluation.application.query.patients.search.GetSearchPatientsQuery;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final IMediator mediator;

    public PatientController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreatePatientsRequest request) {
        CreatePatientsCommand createCommand = CreatePatientsCommand.fromRequest(request);
        CreatePatientMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPatientsQuery query = new GetSearchPatientsQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
