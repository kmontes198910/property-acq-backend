package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.admision.create.CreateAdmisionCommand;
import com.kynsoft.cirugia.application.command.admision.create.CreateAdmisionRequest;
import com.kynsoft.cirugia.application.command.admision.update.UpdateAdmisionCommand;
import com.kynsoft.cirugia.application.command.admision.update.UpdateAdmisionRequest;
import com.kynsoft.cirugia.application.query.admision.AdmisionResponse;
import com.kynsoft.cirugia.application.query.admision.getbyid.GetAdmisionByIdQuery;
import com.kynsoft.cirugia.application.query.admision.getbysurgeryid.GetAdmisionBySurgeryIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for managing admission operations
 */
@RestController
@RequestMapping("/api/admissions")
@RequiredArgsConstructor
public class AdmisionController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    /**
     * Creates a new admission
     *
     * @param request Request with admission data to create
     * @return ResponseEntity with HTTP status
     */
    @PostMapping
    public ResponseEntity<Void> createAdmision(@RequestBody CreateAdmisionRequest request,
                                               @RequestHeader(value = USER_ID_HEADER, required = true) String userId) {
        // Get the current authenticated user


        CreateAdmisionCommand command = CreateAdmisionCommand.builder()
                .id(UUID.randomUUID())
                .surgeryId(request.getSurgeryId())
                .room(request.getRoom())
                .bed(request.getBed())
                .observations(request.getObservations())
                .createdBy(UUID.fromString(userId))
                .build();

        mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Updates an existing admission
     *
     * @param id ID of the admission to update
     * @param request Request with updated admission data
     * @return ResponseEntity
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAdmision(@PathVariable UUID id, @RequestBody UpdateAdmisionRequest request,
                                               @RequestHeader(value = USER_ID_HEADER, required = true) String userId) {


        UpdateAdmisionCommand command = UpdateAdmisionCommand.builder()
                .id(id)
                .room(request.getRoom())
                .bed(request.getBed())
                .observations(request.getObservations())
                .updatedBy(UUID.fromString(userId))
                .build();

        mediator.send(command);
        return ResponseEntity.noContent().build();
    }

    /**
     * Gets an admission by its ID
     *
     * @param id ID of the admission
     * @return ResponseEntity with the admission or notFound if it doesn't exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdmisionResponse> getAdmisionById(@PathVariable UUID id) {
        GetAdmisionByIdQuery query = new GetAdmisionByIdQuery(id);
        AdmisionResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    /**
     * Gets the admission associated with a surgery
     *
     * @param surgeryId ID of the surgery
     * @return ResponseEntity with the admission or notFound if it doesn't exist
     */
    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<AdmisionResponse> getAdmisionBySurgeryId(@PathVariable UUID surgeryId) {
        GetAdmisionBySurgeryIdQuery query = new GetAdmisionBySurgeryIdQuery(surgeryId);
        AdmisionResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

}