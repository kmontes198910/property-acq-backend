package com.kynsof.identity.controller;


import com.kynsof.identity.application.command.manageRole.create.CreateManageRoleCommand;
import com.kynsof.identity.application.command.manageRole.create.CreateManageRoleMessage;
import com.kynsof.identity.application.command.manageRole.create.CreateManageRoleRequest;
import com.kynsof.identity.application.command.manageRole.delete.DeleteManageRoleCommand;
import com.kynsof.identity.application.command.manageRole.delete.DeleteManageRoleMessage;
import com.kynsof.identity.application.command.manageRole.deleteAll.DeleteAllManageRoleCommand;
import com.kynsof.identity.application.command.manageRole.deleteAll.DeleteAllManageRoleMessage;
import com.kynsof.identity.application.command.manageRole.deleteAll.DeleteAllManageRoleRequest;
import com.kynsof.identity.application.command.manageRole.update.UpdateManageRoleCommand;
import com.kynsof.identity.application.command.manageRole.update.UpdateManageRoleMessage;
import com.kynsof.identity.application.command.manageRole.update.UpdateManageRoleRequest;
import com.kynsof.identity.application.query.manageRole.getByid.FindManageRoleByIdQuery;
import com.kynsof.identity.application.query.manageRole.getByid.ManageRoleResponse;
import com.kynsof.identity.application.query.manageRole.search.GetSearchManageRoleQuery;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/role")
public class ManageRoleController {
    private final IMediator mediator;

    public ManageRoleController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateManageRoleRequest request) {
        CreateManageRoleCommand createCommand = CreateManageRoleCommand.fromRequest(request);
        CreateManageRoleMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody UpdateManageRoleRequest request) {

        UpdateManageRoleCommand command = UpdateManageRoleCommand.fromRequest(request, id);
        UpdateManageRoleMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindManageRoleByIdQuery query = new FindManageRoleByIdQuery(id);
        ManageRoleResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);

        GetSearchManageRoleQuery query = new GetSearchManageRoleQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {

        DeleteManageRoleCommand command = new DeleteManageRoleCommand(id);
        DeleteManageRoleMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> delete(@RequestBody DeleteAllManageRoleRequest request) {

        DeleteAllManageRoleCommand command = new DeleteAllManageRoleCommand(request.getRoles());
        DeleteAllManageRoleMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }

}
