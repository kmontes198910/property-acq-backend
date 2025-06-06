package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.surgery.changestatus.ChangeSurgeryStatusMessage;
import com.kynsoft.cirugia.application.command.surgery.create.CreateSurgeryMessage;
import com.kynsoft.cirugia.application.command.surgery.update.UpdateSurgeryMessage;
import com.kynsoft.cirugia.application.query.surgery.SurgeryListResponse;
import com.kynsoft.cirugia.application.query.surgery.SurgeryResponse;
import com.kynsoft.cirugia.application.query.surgery.getSurgeryByIdPlanification.GetSurgeryPlanificationByIdQuery;
import com.kynsoft.cirugia.application.query.surgery.getbyid.GetSurgeryByIdQuery;
import com.kynsoft.cirugia.application.query.surgery.listbybusiness.ListSurgeriesByBusinessQuery;
import com.kynsoft.cirugia.application.query.surgery.listbypatient.ListSurgeriesByPatientQuery;
import com.kynsoft.cirugia.application.query.surgery.search.SearchSurgeriesQuery;
import com.kynsoft.cirugia.application.command.surgery.create.CreateSurgeryCommand;
import com.kynsoft.cirugia.application.command.surgery.create.CreateSurgeryRequest;
import com.kynsoft.cirugia.application.command.surgery.update.UpdateSurgeryCommand;
import com.kynsoft.cirugia.application.command.surgery.update.UpdateSurgeryRequest;
import com.kynsoft.cirugia.application.command.surgery.changestatus.ChangeSurgeryStatusCommand;
import com.kynsoft.cirugia.application.command.surgery.changestatus.ChangeSurgeryStatusRequest;
import com.kynsoft.cirugia.application.command.surgery.delete.DeleteSurgeryCommand;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/surgeries")
public class SurgeryController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

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
    public ResponseEntity<SurgeryResponse> getById(@PathVariable UUID id,
                                                   @RequestHeader(USER_ID_HEADER) String userId) {
        GetSurgeryByIdQuery query = new GetSurgeryByIdQuery(id, UUID.fromString(userId));
        SurgeryResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/planification/{id}")
    public ResponseEntity<SurgeryResponse> getPlanificationById(@PathVariable UUID id,
                                                   @RequestHeader(USER_ID_HEADER) String userId) {
        GetSurgeryPlanificationByIdQuery query = new GetSurgeryPlanificationByIdQuery(id, UUID.fromString(userId));
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


    @PostMapping
    public ResponseEntity<?> createSurgery(@RequestBody CreateSurgeryRequest request,
                                            @RequestHeader(USER_ID_HEADER) String userId,
                                            @RequestHeader(USER_NAME_HEADER) String userName) {
        CreateSurgeryCommand command = CreateSurgeryCommand.fromRequest(request, UUID.fromString(userId));
        CreateSurgeryMessage response =mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSurgery(
            @PathVariable UUID id,
            @RequestBody UpdateSurgeryRequest request,
            @RequestHeader(USER_ID_HEADER) String userId){
        request.setSurgeryId(id);
        UpdateSurgeryCommand command = UpdateSurgeryCommand.fromRequest(request, UUID.fromString(userId));
        UpdateSurgeryMessage response =mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeSurgeryStatus(
            @PathVariable UUID id,
            @RequestBody ChangeSurgeryStatusRequest request,
            @RequestHeader(USER_ID_HEADER) String userId) {
        ChangeSurgeryStatusCommand command = ChangeSurgeryStatusCommand.fromRequest(id, request, UUID.fromString(userId));
        ChangeSurgeryStatusMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSurgery(@PathVariable UUID id) {
        DeleteSurgeryCommand command = new DeleteSurgeryCommand(id);
        mediator.send(command);
        return ResponseEntity.noContent().build();
    }
}