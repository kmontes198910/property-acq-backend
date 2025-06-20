package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.manageRole.update.UpdateManageRoleCommand;
import com.kynsoft.propertyacqcenter.application.command.manageRole.update.UpdateManageRoleMessage;
import com.kynsoft.propertyacqcenter.application.command.manageRole.update.UpdateManageRoleRequest;
import com.kynsoft.propertyacqcenter.application.query.manageRole.search.GetSearchManageRoleQuery;
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

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateManageRoleRequest request) {
        UpdateManageRoleCommand command = UpdateManageRoleCommand.fromRequest(id, request);
        UpdateManageRoleMessage message = mediator.send(command);

        return ResponseEntity.ok(message);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchManageRoleQuery query = new GetSearchManageRoleQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
