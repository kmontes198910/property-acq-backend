package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.prescribedMedication.create.CreatePrescribedMedicationCommand;
import com.kynsof.hospitalizationService.application.command.prescribedMedication.create.CreatePrescribedMedicationMessage;
import com.kynsof.hospitalizationService.application.command.prescribedMedication.create.CreatePrescribedMedicationRequest;
import com.kynsof.hospitalizationService.application.command.prescribedMedication.delete.DeletePrescribedMedicationCommand;
import com.kynsof.hospitalizationService.application.command.prescribedMedication.delete.DeletePrescribedMedicationMessage;
import com.kynsof.hospitalizationService.application.command.prescribedMedication.update.UpdatePrescribedMedicationCommand;
import com.kynsof.hospitalizationService.application.command.prescribedMedication.update.UpdatePrescribedMedicationMessage;
import com.kynsof.hospitalizationService.application.command.prescribedMedication.update.UpdatePrescribedMedicationRequest;
import com.kynsof.hospitalizationService.application.query.prescribedMedication.getById.GetByIdPrescribedMedicationQuery;
import com.kynsof.hospitalizationService.application.query.prescribedMedication.search.GetSearchPrescribedMedicationQuery;
import com.kynsof.hospitalizationService.application.response.PrescribedMedicationResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescribed-medication")
public class PrescribedMedicationController {

    private final IMediator mediator;

    public PrescribedMedicationController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreatePrescribedMedicationMessage> createAllergy(@RequestBody CreatePrescribedMedicationRequest request) {
        CreatePrescribedMedicationCommand createCommand = CreatePrescribedMedicationCommand.fromRequest(request);
        CreatePrescribedMedicationMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdatePrescribedMedicationRequest request) {

        UpdatePrescribedMedicationCommand command = UpdatePrescribedMedicationCommand.fromRequest(request, id);
        UpdatePrescribedMedicationMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeletePrescribedMedicationCommand command = new DeletePrescribedMedicationCommand(id);
        DeletePrescribedMedicationMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdPrescribedMedicationQuery query = new GetByIdPrescribedMedicationQuery(id);
        PrescribedMedicationResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPrescribedMedicationQuery query = new GetSearchPrescribedMedicationQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
