package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.emergencyCase.create.CreateEmergencyCaseCommand;
import com.kynsof.hospitalizationService.application.command.emergencyCase.create.CreateEmergencyCaseMessage;
import com.kynsof.hospitalizationService.application.command.emergencyCase.create.CreateEmergencyCaseRequest;
import com.kynsof.hospitalizationService.application.command.emergencyCase.update.UpdateEmergencyCaseCommand;
import com.kynsof.hospitalizationService.application.command.emergencyCase.update.UpdateEmergencyCaseMessage;
import com.kynsof.hospitalizationService.application.command.emergencyCase.update.UpdateEmergencyCaseRequest;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
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

}
