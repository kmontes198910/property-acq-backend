package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.postOperative.create.CreatePostOperativeCommand;
import com.kynsoft.cirugia.application.command.postOperative.create.CreatePostOperativeMessage;
import com.kynsoft.cirugia.application.command.postOperative.create.CreatePostOperativeRequest;
import com.kynsoft.cirugia.application.command.postOperative.update.UpdatePostOperativeCommand;
import com.kynsoft.cirugia.application.command.postOperative.update.UpdatePostOperativeMessage;
import com.kynsoft.cirugia.application.command.postOperative.update.UpdatePostOperativeRequest;
import com.kynsoft.cirugia.application.query.postOperative.getBySurgeryId.GetPostOperativeBySurgeryIdQuery;
import com.kynsoft.cirugia.application.query.postOperative.getBySurgeryId.PostOperativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/post-operative")
@Slf4j
public class PostOperativeController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";
    public PostOperativeController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody CreatePostOperativeRequest request,
                       @RequestHeader(value = USER_ID_HEADER, required = true) String userId) {
        log.info("Creating new PostOperative for surgery ID: {}", request.getSurgeryId());
        
        CreatePostOperativeCommand command = CreatePostOperativeCommand.fromRequest(request, UUID.fromString(userId));
        CreatePostOperativeMessage response =mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdatePostOperativeRequest request,
                                    @RequestHeader(value = USER_ID_HEADER, required = true) String userId) {
        log.info("Updating PostOperative with ID: {}", id);
        UpdatePostOperativeCommand command = UpdatePostOperativeCommand.fromRequest(id,request, UUID.fromString(userId));
        UpdatePostOperativeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<?> getBySurgeryId(@PathVariable("surgeryId") UUID surgeryId) {
        log.info("Getting PostOperative by Surgery ID: {}", surgeryId);
        
        GetPostOperativeBySurgeryIdQuery query = new GetPostOperativeBySurgeryIdQuery(surgeryId);
        PostOperativeResponse postOperative = mediator.send(query);
        return ResponseEntity.ok(postOperative);

    }
}