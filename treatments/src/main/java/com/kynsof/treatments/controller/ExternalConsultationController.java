package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.command.externalConsultation.create.CreateExternalConsultationCommand;
import com.kynsof.treatments.application.command.externalConsultation.create.CreateExternalConsultationMessage;
import com.kynsof.treatments.application.command.externalConsultation.create.CreateExternalConsultationRequest;
import com.kynsof.treatments.application.command.externalConsultation.delete.PatientExternalConsultationMessage;
import com.kynsof.treatments.application.command.externalConsultation.delete.PatientsExternalConsultationCommand;
import com.kynsof.treatments.application.command.externalConsultation.update.UpdateExternalConsultationAllCommand;
import com.kynsof.treatments.application.command.externalConsultation.update.UpdateExternalConsultationAllMessage;
import com.kynsof.treatments.application.command.externalConsultation.update.UpdateExternalConsultationAllRequest;
import com.kynsof.treatments.application.query.externalConsultation.getById.FindByIdExternalConsultationQuery;
import com.kynsof.treatments.application.query.externalConsultation.getall.ExternalConsultationResponse;
import com.kynsof.treatments.application.query.externalConsultation.search.GetSearchExternalConsultationQuery;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/external-consultation")
public class ExternalConsultationController {

    private final IMediator mediator;

    public ExternalConsultationController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateExternalConsultationRequest request) {
        CreateExternalConsultationCommand createCommand = CreateExternalConsultationCommand.fromRequest(request);
        CreateExternalConsultationMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getPageSize())
                                            .withSort(Sort.by("consultationTime").descending());

        GetSearchExternalConsultationQuery query = new GetSearchExternalConsultationQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

//    @GetMapping("/all")
//    public ResponseEntity<PaginatedResponse> getAll(@RequestParam(defaultValue = "20") Integer pageSize,
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "") UUID patientId,
//            @RequestParam(defaultValue = "") UUID doctorId) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//        GetAllExternalConsultationQuery query = new GetAllExternalConsultationQuery(pageable, doctorId, patientId);
//        PaginatedResponse response = mediator.send(query);
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ExternalConsultationResponse> getById(@PathVariable UUID id) {

        FindByIdExternalConsultationQuery query = new FindByIdExternalConsultationQuery(id);
        ExternalConsultationResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> updateAll(@PathVariable UUID id, @RequestBody UpdateExternalConsultationAllRequest request) {
        UpdateExternalConsultationAllCommand command = UpdateExternalConsultationAllCommand.fromRequest(id, request);
        UpdateExternalConsultationAllMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        PatientsExternalConsultationCommand query = new PatientsExternalConsultationCommand(id);
        PatientExternalConsultationMessage response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

}
