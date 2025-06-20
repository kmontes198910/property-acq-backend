package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.manageRole.create.CreateManageRoleCommand;
import com.kynsoft.propertyacqcenter.application.command.manageRole.create.CreateManageRoleMessage;
import com.kynsoft.propertyacqcenter.application.command.manageRole.create.CreateManageRoleRequest;
import com.kynsoft.propertyacqcenter.application.command.manageRole.delete.DeleteManageRoleCommand;
import com.kynsoft.propertyacqcenter.application.command.manageRole.delete.DeleteManageRoleMessage;
import com.kynsoft.propertyacqcenter.application.command.manageRole.update.UpdateManageRoleCommand;
import com.kynsoft.propertyacqcenter.application.command.manageRole.update.UpdateManageRoleMessage;
import com.kynsoft.propertyacqcenter.application.command.manageRole.update.UpdateManageRoleRequest;
import com.kynsoft.propertyacqcenter.application.command.manageRole.updateDocumentType.UpdateManageRoleDocumentTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.manageRole.updateDocumentType.UpdateManageRoleDocumentTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.manageRole.updateDocumentType.UpdateManageRoleDocumentTypeRequest;
import com.kynsoft.propertyacqcenter.application.query.manageRole.getById.GetByIdManageRoleQuery;
import com.kynsoft.propertyacqcenter.application.query.manageRole.search.GetSearchManageRoleQuery;
import com.kynsoft.propertyacqcenter.application.response.ManageRoleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final IMediator mediator;

    public RolesController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateManageRoleMessage> create(@RequestBody CreateManageRoleRequest request) {
        CreateManageRoleCommand createCommand = CreateManageRoleCommand.fromRequest(request);
        CreateManageRoleMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateManageRoleRequest request) {

        UpdateManageRoleCommand command = UpdateManageRoleCommand.fromRequest(request, id);
        UpdateManageRoleMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteManageRoleCommand command = new DeleteManageRoleCommand(id);
        DeleteManageRoleMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdManageRoleQuery query = new GetByIdManageRoleQuery(id);
        ManageRoleResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchManageRoleQuery query = new GetSearchManageRoleQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    //////
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateDocumentType(@PathVariable("id") UUID id, @RequestBody UpdateManageRoleDocumentTypeRequest request) {
        UpdateManageRoleDocumentTypeCommand command = UpdateManageRoleDocumentTypeCommand.fromRequest(id, request);
        UpdateManageRoleDocumentTypeMessage message = mediator.send(command);

        return ResponseEntity.ok(message);
    }
    //////

}
