package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.teamAssignment.create.CreateTeamAssignmentCommand;
import com.kynsoft.propertyacqcenter.application.command.teamAssignment.create.CreateTeamAssignmentMessage;
import com.kynsoft.propertyacqcenter.application.command.teamAssignment.create.CreateTeamAssignmentRequest;
import com.kynsoft.propertyacqcenter.application.command.teamAssignment.delete.DeleteTeamAssignmentCommand;
import com.kynsoft.propertyacqcenter.application.command.teamAssignment.delete.DeleteTeamAssignmentMessage;
import com.kynsoft.propertyacqcenter.application.command.teamAssignment.update.UpdateTeamAssignmentCommand;
import com.kynsoft.propertyacqcenter.application.command.teamAssignment.update.UpdateTeamAssignmentMessage;
import com.kynsoft.propertyacqcenter.application.command.teamAssignment.update.UpdateTeamAssignmentRequest;
import com.kynsoft.propertyacqcenter.application.query.teamAssignment.getById.GetByIdTeamAssignmentQuery;
import com.kynsoft.propertyacqcenter.application.query.teamAssignment.getByPropertyId.FindByTeamAssignmentByPropertyIdQuery;
import com.kynsoft.propertyacqcenter.application.query.teamAssignment.search.GetSearchTeamAssignmentQuery;
import com.kynsoft.propertyacqcenter.application.response.TeamAssignmentResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team_assignment")
public class TeamAssignmentController {

    private final IMediator mediator;

    public TeamAssignmentController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateTeamAssignmentMessage> createAllergy(@RequestBody CreateTeamAssignmentRequest request) {
        CreateTeamAssignmentCommand createCommand = CreateTeamAssignmentCommand.fromRequest(request);
        CreateTeamAssignmentMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateTeamAssignmentRequest request) {

        UpdateTeamAssignmentCommand command = UpdateTeamAssignmentCommand.fromRequest(request, id);
        UpdateTeamAssignmentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteTeamAssignmentCommand command = new DeleteTeamAssignmentCommand(id);
        DeleteTeamAssignmentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdTeamAssignmentQuery query = new GetByIdTeamAssignmentQuery(id);
        TeamAssignmentResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/property/{id}")
    public ResponseEntity<?> getByPropertyId(@PathVariable String id) {

        FindByTeamAssignmentByPropertyIdQuery query = new FindByTeamAssignmentByPropertyIdQuery(id);
        TeamAssignmentResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchTeamAssignmentQuery query = new GetSearchTeamAssignmentQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
