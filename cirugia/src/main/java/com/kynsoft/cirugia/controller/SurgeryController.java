package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.query.SurgeryListResponse;
import com.kynsoft.cirugia.application.query.SurgeryResponse;
import com.kynsoft.cirugia.application.query.surgery.getbyid.GetSurgeryByIdQuery;
import com.kynsoft.cirugia.application.query.surgery.listbybusiness.ListSurgeriesByBusinessQuery;
import com.kynsoft.cirugia.application.query.surgery.listbypatient.ListSurgeriesByPatientQuery;
import com.kynsoft.cirugia.application.query.surgery.search.SearchSurgeriesQuery;
import com.kynsoft.cirugia.application.query.surgery.listbydaterange.ListSurgeriesByDateRangeRequest;
import com.kynsoft.cirugia.application.query.surgery.listbydaterange.ListSurgeriesByDateRangeQuery;
import com.kynsoft.cirugia.application.command.create.CreateSurgeryCommand;
import com.kynsoft.cirugia.application.command.create.CreateSurgeryRequest;
import com.kynsoft.cirugia.application.command.update.UpdateSurgeryCommand;
import com.kynsoft.cirugia.application.command.update.UpdateSurgeryRequest;
import com.kynsoft.cirugia.application.command.changestatus.ChangeSurgeryStatusCommand;
import com.kynsoft.cirugia.application.command.changestatus.ChangeSurgeryStatusRequest;
import com.kynsoft.cirugia.application.command.delete.DeleteSurgeryCommand;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/surgeries")
public class SurgeryController {

    private final IMediator mediator;

    public SurgeryController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);

        SearchSurgeriesQuery query = new SearchSurgeriesQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SurgeryResponse> getById(@PathVariable UUID id) {
        GetSurgeryByIdQuery query = new GetSurgeryByIdQuery(id);
        SurgeryResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<SurgeryListResponse> listByBusiness(@PathVariable UUID businessId) {
        ListSurgeriesByBusinessQuery query = new ListSurgeriesByBusinessQuery(businessId);
        SurgeryListResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<SurgeryListResponse> listByPatient(@PathVariable UUID patientId) {
        ListSurgeriesByPatientQuery query = new ListSurgeriesByPatientQuery(patientId);
        SurgeryListResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/date-range")
    public ResponseEntity<SurgeryListResponse> listByDateRange(@RequestBody ListSurgeriesByDateRangeRequest request) {
        ListSurgeriesByDateRangeQuery query = new ListSurgeriesByDateRangeQuery(
                request.getStartDate(), 
                request.getEndDate(), 
                request.getBusinessId()
        );
        SurgeryListResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createSurgery(@RequestBody CreateSurgeryRequest request) {
        CreateSurgeryCommand command = CreateSurgeryCommand.fromRequest(request);
        mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSurgery(
            @PathVariable UUID id,
            @RequestBody UpdateSurgeryRequest request) {
        request.setSurgeryId(id);
        UpdateSurgeryCommand command = UpdateSurgeryCommand.fromRequest(request);
        mediator.send(command);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeSurgeryStatus(
            @PathVariable UUID id,
            @RequestBody ChangeSurgeryStatusRequest request) {
        request.setSurgeryId(id);
        ChangeSurgeryStatusCommand command = ChangeSurgeryStatusCommand.fromRequest(request);
        mediator.send(command);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSurgery(@PathVariable UUID id) {
        DeleteSurgeryCommand command = new DeleteSurgeryCommand(id);
        mediator.send(command);
        return ResponseEntity.noContent().build();
    }
}