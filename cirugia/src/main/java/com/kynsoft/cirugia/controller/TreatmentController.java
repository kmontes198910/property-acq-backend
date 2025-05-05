package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.treatment.create.CreateTreatmentCommand;
import com.kynsoft.cirugia.application.command.treatment.create.CreateTreatmentRequest;
import com.kynsoft.cirugia.application.command.treatment.create.CreateTreatmentMessage;
import com.kynsoft.cirugia.application.command.treatment.update.UpdateTreatmentCommand;
import com.kynsoft.cirugia.application.command.treatment.update.UpdateTreatmentRequest;
import com.kynsoft.cirugia.application.command.treatment.update.UpdateTreatmentMessage;
import com.kynsoft.cirugia.application.command.treatment.delete.DeleteTreatmentCommand;
import com.kynsoft.cirugia.application.command.treatment.delete.DeleteTreatmentMessage;
import com.kynsoft.cirugia.application.query.treatment.getbyid.GetTreatmentByIdQuery;
import com.kynsoft.cirugia.application.query.treatment.getbyid.TreatmentResponse;
import com.kynsoft.cirugia.application.query.treatment.getbysurgeryid.GetTreatmentsBySurgeryIdQuery;
import com.kynsoft.cirugia.application.query.treatment.getbysurgeryid.TreatmentsListResponse;
import com.kynsoft.cirugia.application.query.treatment.search.SearchTreatmentsQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/treatments")
@RequiredArgsConstructor
public class TreatmentController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        log.info("Fetching treatment with ID: {}", id);
        GetTreatmentByIdQuery query = new GetTreatmentByIdQuery(id);
        TreatmentResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<TreatmentsListResponse> findBySurgeryId(@PathVariable UUID surgeryId) {
        log.info("Fetching treatments for surgery ID: {}", surgeryId);
        GetTreatmentsBySurgeryIdQuery query = new GetTreatmentsBySurgeryIdQuery(surgeryId);
        TreatmentsListResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody CreateTreatmentRequest request,
                                   @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        log.info("Creating new treatment for surgery ID: {}", request.getSurgeryId());
        CreateTreatmentCommand command = CreateTreatmentCommand.fromRequest(request, userId);
        CreateTreatmentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, 
                                    @RequestBody UpdateTreatmentRequest request,
                                    @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
                                    @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        log.info("Updating treatment with ID: {}", id);
        UpdateTreatmentCommand command = UpdateTreatmentCommand.fromRequest(request, id, userId);
        UpdateTreatmentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        log.info("Deleting treatment with ID: {}", id);
        DeleteTreatmentCommand command = new DeleteTreatmentCommand(id);
        DeleteTreatmentMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        log.info("Searching treatments with filters: {}", request.getFilter());
        Pageable pageable = PageableUtil.createPageable(request);
        List<FilterCriteria> filterCriteria = request.getFilter();
        SearchTreatmentsQuery query = new SearchTreatmentsQuery(pageable, filterCriteria);
        try {
            PaginatedResponse response = mediator.send(query);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error while searching treatments: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}