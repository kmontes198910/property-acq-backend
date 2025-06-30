package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.create.CreatePropertyTeamCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.create.CreatePropertyTeamMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.create.CreatePropertyTeamRequest;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.createAll.CreatePropertyTeamAllCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.createAll.CreatePropertyTeamAllMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.createAll.CreatePropertyTeamAllRequest;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.delete.DeletePropertyTeamCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.delete.DeletePropertyTeamMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.update.UpdatePropertyTeamCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.update.UpdatePropertyTeamMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.update.UpdatePropertyTeamRequest;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.updateAll.UpdatePropertyTeamAllCommand;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.updateAll.UpdatePropertyTeamAllMessage;
import com.kynsoft.propertyacqcenter.application.command.propertyTeam.updateAll.UpdatePropertyTeamAllRequest;
import com.kynsoft.propertyacqcenter.application.query.propertyTeam.getById.GetByIdPropertyTeamQuery;
import com.kynsoft.propertyacqcenter.application.query.propertyTeam.search.GetSearchPropertyTeamQuery;
import com.kynsoft.propertyacqcenter.application.response.PropertyTeamResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property-team")
public class PropertyTeamController {

    private final IMediator mediator;

    public PropertyTeamController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("/all")
    public ResponseEntity<?> create(@RequestBody CreatePropertyTeamAllRequest request) {
        CreatePropertyTeamAllCommand createCommand = CreatePropertyTeamAllCommand.fromRequest(request);
        CreatePropertyTeamAllMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreatePropertyTeamRequest request) {
        CreatePropertyTeamCommand createCommand = CreatePropertyTeamCommand.fromRequest(request);
        CreatePropertyTeamMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdatePropertyTeamRequest request) {

        UpdatePropertyTeamCommand command = UpdatePropertyTeamCommand.fromRequest(request, id);
        UpdatePropertyTeamMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/all")
    public ResponseEntity<?> update(@RequestBody UpdatePropertyTeamAllRequest request) {

        UpdatePropertyTeamAllCommand command = UpdatePropertyTeamAllCommand.fromRequest(request);
        UpdatePropertyTeamAllMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeletePropertyTeamCommand command = new DeletePropertyTeamCommand(id);
        DeletePropertyTeamMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdPropertyTeamQuery query = new GetByIdPropertyTeamQuery(id);
        PropertyTeamResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPropertyTeamQuery query = new GetSearchPropertyTeamQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
