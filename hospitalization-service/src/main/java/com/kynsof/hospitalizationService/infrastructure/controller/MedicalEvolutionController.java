package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.medicalEvolution.create.CreateMedicalEvolutionCommand;
import com.kynsof.hospitalizationService.application.command.medicalEvolution.create.CreateMedicalEvolutionMessage;
import com.kynsof.hospitalizationService.application.command.medicalEvolution.create.CreateMedicalEvolutionRequest;
import com.kynsof.hospitalizationService.application.command.medicalEvolution.delete.DeleteMedicalEvaluationCommand;
import com.kynsof.hospitalizationService.application.command.medicalEvolution.delete.DeleteMedicalEvaluationMessage;
import com.kynsof.hospitalizationService.application.command.medicalEvolution.update.UpdateMedicalEvolutionCommand;
import com.kynsof.hospitalizationService.application.command.medicalEvolution.update.UpdateMedicalEvolutionMessage;
import com.kynsof.hospitalizationService.application.command.medicalEvolution.update.UpdateMedicalEvolutionRequest;
import com.kynsof.hospitalizationService.application.query.medicalEvolution.getById.GetByIdMedicalEvolutionQuery;
import com.kynsof.hospitalizationService.application.query.medicalEvolution.search.GetSearchMedicalEvolutionQuery;
import com.kynsof.hospitalizationService.application.response.MedicalEvolutionResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-evolution")
public class MedicalEvolutionController {

    private final IMediator mediator;

    public MedicalEvolutionController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateMedicalEvolutionMessage> createAllergy(@RequestBody CreateMedicalEvolutionRequest request) {
        CreateMedicalEvolutionCommand createCommand = CreateMedicalEvolutionCommand.fromRequest(request);
        CreateMedicalEvolutionMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateMedicalEvolutionRequest request) {

        UpdateMedicalEvolutionCommand command = UpdateMedicalEvolutionCommand.fromRequest(request, id);
        UpdateMedicalEvolutionMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteMedicalEvaluationCommand command = new DeleteMedicalEvaluationCommand(id);
        DeleteMedicalEvaluationMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdMedicalEvolutionQuery query = new GetByIdMedicalEvolutionQuery(id);
        MedicalEvolutionResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchMedicalEvolutionQuery query = new GetSearchMedicalEvolutionQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
