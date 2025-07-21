package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.email.create.CreateEmailCommand;
import com.kynsoft.propertyacqcenter.application.command.email.create.CreateEmailMessage;
import com.kynsoft.propertyacqcenter.application.command.email.create.CreateEmailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class SendEmailController {

    private final IMediator mediator;

    public SendEmailController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateEmailRequest request) {
        CreateEmailCommand createCommand = CreateEmailCommand.fromRequest(request);
        CreateEmailMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

}
