package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.medicalPrescription.create.CreateMedicalPrescriptionCommand;
import com.kynsof.hospitalizationService.application.command.medicalPrescription.create.CreateMedicalPrescriptionMessage;
import com.kynsof.hospitalizationService.application.command.medicalPrescription.create.CreateMedicalPrescriptionRequest;
import com.kynsof.hospitalizationService.application.command.medicalPrescription.delete.DeleteMedicalPrescriptionCommand;
import com.kynsof.hospitalizationService.application.command.medicalPrescription.delete.DeleteMedicalPrescriptionMessage;
import com.kynsof.hospitalizationService.application.command.medicalPrescription.update.UpdateMedicalPrescriptionCommand;
import com.kynsof.hospitalizationService.application.command.medicalPrescription.update.UpdateMedicalPrescriptionMessage;
import com.kynsof.hospitalizationService.application.command.medicalPrescription.update.UpdateMedicalPrescriptionRequest;
import com.kynsof.hospitalizationService.application.query.medicalPrescription.getById.GetByIdMedicalPrescriptionQuery;
import com.kynsof.hospitalizationService.application.query.medicalPrescription.search.GetSearchMedicalPrescriptionQuery;
import com.kynsof.hospitalizationService.application.response.MedicalPrescriptionResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-prescription")
public class MedicalPrescriptionController {

    private final IMediator mediator;

    public MedicalPrescriptionController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateMedicalPrescriptionMessage> createAllergy(@RequestBody CreateMedicalPrescriptionRequest request) {
        CreateMedicalPrescriptionCommand createCommand = CreateMedicalPrescriptionCommand.fromRequest(request);
        CreateMedicalPrescriptionMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateMedicalPrescriptionRequest request) {

        UpdateMedicalPrescriptionCommand command = UpdateMedicalPrescriptionCommand.fromRequest(request, id);
        UpdateMedicalPrescriptionMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteMedicalPrescriptionCommand command = new DeleteMedicalPrescriptionCommand(id);
        DeleteMedicalPrescriptionMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdMedicalPrescriptionQuery query = new GetByIdMedicalPrescriptionQuery(id);
        MedicalPrescriptionResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchMedicalPrescriptionQuery query = new GetSearchMedicalPrescriptionQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
