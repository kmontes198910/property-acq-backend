package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.emergencyCase.create.CreateEmergencyCaseCommand;
import com.kynsof.hospitalizationService.application.command.emergencyCase.create.CreateEmergencyCaseMessage;
import com.kynsof.hospitalizationService.application.command.emergencyCase.create.CreateEmergencyCaseRequest;
import com.kynsof.hospitalizationService.application.command.emergencyCase.delete.DeleteEmergencyCaseCommand;
import com.kynsof.hospitalizationService.application.command.emergencyCase.delete.DeleteEmergencyCaseMessage;
import com.kynsof.hospitalizationService.application.command.emergencyCase.simpleUpdate.SimpleUpdateEmergencyCaseCommand;
import com.kynsof.hospitalizationService.application.command.emergencyCase.simpleUpdate.SimpleUpdateEmergencyCaseMessage;
import com.kynsof.hospitalizationService.application.command.emergencyCase.simpleUpdate.SimpleUpdateEmergencyCaseRequest;
import com.kynsof.hospitalizationService.application.command.emergencyCase.update.UpdateEmergencyCaseCommand;
import com.kynsof.hospitalizationService.application.command.emergencyCase.update.UpdateEmergencyCaseMessage;
import com.kynsof.hospitalizationService.application.command.emergencyCase.update.UpdateEmergencyCaseRequest;
import com.kynsof.hospitalizationService.application.query.emergencyCase.getById.GetByIdEmergencyCaseQuery;
import com.kynsof.hospitalizationService.application.query.emergencyCase.search.GetSearchEmergencyCaseQuery;
import com.kynsof.hospitalizationService.application.response.EmergencyCaseResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyCaseController {

    private final IMediator mediator;

    public EmergencyCaseController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateEmergencyCaseMessage> createAllergy(@RequestBody CreateEmergencyCaseRequest request) {
        CreateEmergencyCaseCommand createCommand = CreateEmergencyCaseCommand.fromRequest(request);
        CreateEmergencyCaseMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateEmergencyCaseRequest request) {

        UpdateEmergencyCaseCommand command = UpdateEmergencyCaseCommand.fromRequest(request, id);
        UpdateEmergencyCaseMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/simple-update/{id}")
    public ResponseEntity<?> simpleUpdate(@PathVariable("id") UUID id, @RequestBody SimpleUpdateEmergencyCaseRequest request) {

        SimpleUpdateEmergencyCaseCommand command = SimpleUpdateEmergencyCaseCommand.fromRequest(request, id);
        SimpleUpdateEmergencyCaseMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteEmergencyCaseCommand command = new DeleteEmergencyCaseCommand(id);
        DeleteEmergencyCaseMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdEmergencyCaseQuery query = new GetByIdEmergencyCaseQuery(id);
        EmergencyCaseResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchEmergencyCaseQuery query = new GetSearchEmergencyCaseQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
