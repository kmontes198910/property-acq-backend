package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.procedure.create.CreateProcedureCommand;
import com.kynsof.treatments.application.command.procedure.create.CreateProcedureMessage;
import com.kynsof.treatments.application.command.procedure.create.CreateProcedureRequest;
import com.kynsof.treatments.application.command.procedure.delete.ProcedureDeleteCommand;
import com.kynsof.treatments.application.command.procedure.delete.ProcedureDeleteMessage;
import com.kynsof.treatments.application.command.procedure.update.UpdateProcedureCommand;
import com.kynsof.treatments.application.command.procedure.update.UpdateProcedureMessage;
import com.kynsof.treatments.application.command.procedure.update.UpdateProcedureRequest;
import com.kynsof.treatments.application.query.procedure.getAll.ProcedureResponse;
import com.kynsof.treatments.application.query.procedure.getByCode.FindByCodeProcedureQuery;
import com.kynsof.treatments.application.query.procedure.getbyid.FindByIdProcedureQuery;
import com.kynsof.treatments.application.query.procedure.search.GetSearchProcedureQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/procedure")
public class ProcedureController {

    private final IMediator mediator;

    public ProcedureController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateProcedureRequest request) {
        CreateProcedureCommand createCommand = CreateProcedureCommand.fromRequest(request);
        CreateProcedureMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateProcedureMessage> update(@PathVariable("id") UUID id, @RequestBody UpdateProcedureRequest request) {

        UpdateProcedureCommand command = UpdateProcedureCommand.fromRequest(request, id);
        UpdateProcedureMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/all")
//    public ResponseEntity<PaginatedResponse> getAll(@RequestParam(defaultValue = "20") Integer pageSize,
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "") String name,
//            @RequestParam(defaultValue = "") String code,
//            @RequestParam(defaultValue = "") String type) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//        GetAllProcedureQuery query = new GetAllProcedureQuery(pageable, name, code, type);
//        PaginatedResponse response = mediator.send(query);
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping(path = "/by-code/{code}")
    public ResponseEntity<ProcedureResponse> getById(@PathVariable String code) {

        FindByCodeProcedureQuery query = new FindByCodeProcedureQuery(code);
        ProcedureResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProcedureResponse> getById(@PathVariable UUID id) {

        FindByIdProcedureQuery query = new FindByIdProcedureQuery(id);
        ProcedureResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchProcedureQuery query = new GetSearchProcedureQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        ProcedureDeleteCommand query = new ProcedureDeleteCommand(id);
        ProcedureDeleteMessage response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

}
