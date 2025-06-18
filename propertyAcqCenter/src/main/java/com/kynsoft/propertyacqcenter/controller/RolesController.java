package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.manageRole.update.UpdateManageRoleCommand;
import com.kynsoft.propertyacqcenter.application.command.manageRole.update.UpdateManageRoleMessage;
import com.kynsoft.propertyacqcenter.application.command.manageRole.update.UpdateManageRoleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RolesController {

    private final IMediator mediator;

    public RolesController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateManageRoleRequest request) {
        UpdateManageRoleCommand command = UpdateManageRoleCommand.fromRequest(id, request);
        UpdateManageRoleMessage message = mediator.send(command);

        return ResponseEntity.ok(message);
    }
}
