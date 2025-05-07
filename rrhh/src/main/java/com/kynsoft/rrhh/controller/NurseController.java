package com.kynsoft.rrhh.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.rrhh.application.command.nurse.create.CreateNurseCommand;
import com.kynsoft.rrhh.application.command.nurse.create.CreateNurseMessage;
import com.kynsoft.rrhh.application.command.nurse.create.CreateNurseRequest;
import com.kynsoft.rrhh.application.command.nurse.update.UpdateNurseCommand;
import com.kynsoft.rrhh.application.command.nurse.update.UpdateNurseMessage;
import com.kynsoft.rrhh.application.command.nurse.update.UpdateNurseRequest;
import com.kynsoft.rrhh.application.query.nurse.getbyid.FindByIdNurseQuery;
import com.kynsoft.rrhh.application.query.nurse.getbyid.NurseResponse;
import com.kynsoft.rrhh.application.query.nurse.search.GetSearchNurseQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/nurse")
public class NurseController {

    private final IMediator mediator;

    public NurseController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateNurseRequest request) {
        CreateNurseCommand createCommand = CreateNurseCommand.fromRequest(request);
        CreateNurseMessage response = mediator.send(createCommand);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        FindByIdNurseQuery query = new FindByIdNurseQuery(id);
        NurseResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchNurseQuery query = new GetSearchNurseQuery(
                pageable,
                request.getFilter()
        );
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateNurseRequest request) {
        UpdateNurseCommand updateCommand = UpdateNurseCommand.fromRequest(request, id);
        UpdateNurseMessage response = mediator.send(updateCommand);
        return ResponseEntity.ok(response);
    }
}