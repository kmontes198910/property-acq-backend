package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.postOperative.create.CreatePostOperativeCommand;
import com.kynsoft.cirugia.application.command.postOperative.create.CreatePostOperativeRequest;
import com.kynsoft.cirugia.application.command.postOperative.update.UpdatePostOperativeCommand;
import com.kynsoft.cirugia.application.query.postOperative.getBySurgeryId.GetPostOperativeBySurgeryIdQuery;
import com.kynsoft.cirugia.domain.dto.PostOperative;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
    public void create(@RequestBody CreatePostOperativeRequest request,
                       @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
                       @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        log.info("Creating new PostOperative for surgery ID: {}", request.getSurgeryId());
        
        CreatePostOperativeCommand command = CreatePostOperativeCommand.fromRequest(request, UUID.fromString(userId));
        mediator.send(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") UUID id, @RequestBody UpdatePostOperativeCommand command) {
        log.info("Updating PostOperative with ID: {}", id);
        
        command.setId(id);
        mediator.send(command);
    }

    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<PostOperative> getBySurgeryId(@PathVariable("surgeryId") UUID surgeryId) {
        log.info("Getting PostOperative by Surgery ID: {}", surgeryId);
        
        GetPostOperativeBySurgeryIdQuery query = new GetPostOperativeBySurgeryIdQuery(surgeryId);
        Optional<PostOperative> postOperative = mediator.send(query);
        
        return postOperative
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}