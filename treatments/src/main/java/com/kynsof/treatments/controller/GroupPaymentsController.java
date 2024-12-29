package com.kynsof.treatments.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.groupPayment.create.CreateGroupPaymentCommand;
import com.kynsof.treatments.application.command.groupPayment.create.CreateGroupPaymentMessage;
import com.kynsof.treatments.application.command.groupPayment.create.CreateGroupPaymentRequest;
import com.kynsof.treatments.application.command.groupPayment.delete.DeleteGroupPaymentCommand;
import com.kynsof.treatments.application.command.groupPayment.delete.DeleteGroupPaymentMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/group-payments")
public class GroupPaymentsController {

    private final IMediator mediator;

    public GroupPaymentsController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateGroupPaymentRequest requests) {

        CreateGroupPaymentCommand command = CreateGroupPaymentCommand.fromRequest(requests);
        CreateGroupPaymentMessage message = mediator.send(command);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@RequestParam UUID id) {
        DeleteGroupPaymentMessage message = mediator.send(new DeleteGroupPaymentCommand(id));
       return ResponseEntity.ok(message);
    }

}
