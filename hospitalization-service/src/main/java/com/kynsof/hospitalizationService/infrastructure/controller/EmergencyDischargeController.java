package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.emergencyDischarge.create.CreateEmergencyDischargeCommand;
import com.kynsof.hospitalizationService.application.command.emergencyDischarge.create.CreateEmergencyDischargeMessage;
import com.kynsof.hospitalizationService.application.command.emergencyDischarge.create.CreateEmergencyDischargeRequest;
import com.kynsof.hospitalizationService.application.command.emergencyDischarge.delete.DeleteEmergencyDischargeCommand;
import com.kynsof.hospitalizationService.application.command.emergencyDischarge.delete.DeleteEmergencyDischargeMessage;
import com.kynsof.hospitalizationService.application.command.emergencyDischarge.update.UpdateEmergencyDischargeCommand;
import com.kynsof.hospitalizationService.application.command.emergencyDischarge.update.UpdateEmergencyDischargeMessage;
import com.kynsof.hospitalizationService.application.command.emergencyDischarge.update.UpdateEmergencyDischargeRequest;
import com.kynsof.hospitalizationService.application.query.emergencyDischarge.getById.GetByIdEmergencyDischargeQuery;
import com.kynsof.hospitalizationService.application.query.emergencyDischarge.search.GetSearchEmergencyDischargeQuery;
import com.kynsof.hospitalizationService.application.response.EmergencyDischargeResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency-discharge")
public class EmergencyDischargeController {

    private final IMediator mediator;

    public EmergencyDischargeController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateEmergencyDischargeMessage> createAllergy(@RequestBody CreateEmergencyDischargeRequest request) {
        CreateEmergencyDischargeCommand createCommand = CreateEmergencyDischargeCommand.fromRequest(request);
        CreateEmergencyDischargeMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateEmergencyDischargeRequest request) {

        UpdateEmergencyDischargeCommand command = UpdateEmergencyDischargeCommand.fromRequest(request, id);
        UpdateEmergencyDischargeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteEmergencyDischargeCommand command = new DeleteEmergencyDischargeCommand(id);
        DeleteEmergencyDischargeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdEmergencyDischargeQuery query = new GetByIdEmergencyDischargeQuery(id);
        EmergencyDischargeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchEmergencyDischargeQuery query = new GetSearchEmergencyDischargeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
