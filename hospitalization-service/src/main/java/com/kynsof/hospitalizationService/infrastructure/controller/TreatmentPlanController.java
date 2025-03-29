package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.treatmentPlan.create.CreateTreatmentPlanCommand;
import com.kynsof.hospitalizationService.application.command.treatmentPlan.create.CreateTreatmentPlanMessage;
import com.kynsof.hospitalizationService.application.command.treatmentPlan.create.CreateTreatmentPlanRequest;
import com.kynsof.hospitalizationService.application.command.treatmentPlan.delete.DeleteTreatmentPlanCommand;
import com.kynsof.hospitalizationService.application.command.treatmentPlan.delete.DeleteTreatmentPlanMessage;
import com.kynsof.hospitalizationService.application.command.treatmentPlan.update.UpdateTreatmentPlanCommand;
import com.kynsof.hospitalizationService.application.command.treatmentPlan.update.UpdateTreatmentPlanMessage;
import com.kynsof.hospitalizationService.application.command.treatmentPlan.update.UpdateTreatmentPlanRequest;
import com.kynsof.hospitalizationService.application.query.emergencyCase.search.GetSearchEmergencyCaseQuery;
import com.kynsof.hospitalizationService.application.query.treatmentPlan.getById.GetByIdTreatmentPlanQuery;
import com.kynsof.hospitalizationService.application.query.treatmentPlan.search.GetSearchTreatmentPlanQuery;
import com.kynsof.hospitalizationService.application.response.TreatmentPlanResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/treatment-plan")
public class TreatmentPlanController {

    private final IMediator mediator;

    public TreatmentPlanController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateTreatmentPlanMessage> createAllergy(@RequestBody CreateTreatmentPlanRequest request) {
        CreateTreatmentPlanCommand createCommand = CreateTreatmentPlanCommand.fromRequest(request);
        CreateTreatmentPlanMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateTreatmentPlanRequest request) {

        UpdateTreatmentPlanCommand command = UpdateTreatmentPlanCommand.fromRequest(request, id);
        UpdateTreatmentPlanMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteTreatmentPlanCommand command = new DeleteTreatmentPlanCommand(id);
        DeleteTreatmentPlanMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdTreatmentPlanQuery query = new GetByIdTreatmentPlanQuery(id);
        TreatmentPlanResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchTreatmentPlanQuery query = new GetSearchTreatmentPlanQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
