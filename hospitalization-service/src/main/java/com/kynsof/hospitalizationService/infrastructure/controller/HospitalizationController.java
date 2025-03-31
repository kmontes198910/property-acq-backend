package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.hospitalization.create.CreateHospitalizationCommand;
import com.kynsof.hospitalizationService.application.command.hospitalization.create.CreateHospitalizationMessage;
import com.kynsof.hospitalizationService.application.command.hospitalization.create.CreateHospitalizationRequest;
import com.kynsof.hospitalizationService.application.command.hospitalization.delete.DeleteHospitalizationCommand;
import com.kynsof.hospitalizationService.application.command.hospitalization.delete.DeleteHospitalizationMessage;
import com.kynsof.hospitalizationService.application.command.hospitalization.update.UpdateHospitalizationCommand;
import com.kynsof.hospitalizationService.application.command.hospitalization.update.UpdateHospitalizationMessage;
import com.kynsof.hospitalizationService.application.command.hospitalization.update.UpdateHospitalizationRequest;
import com.kynsof.hospitalizationService.application.query.hospitalization.getById.GetByIdHospitalizationQuery;
import com.kynsof.hospitalizationService.application.query.hospitalization.search.GetSearchHospitalizationQuery;
import com.kynsof.hospitalizationService.application.response.HospitalizationResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospitalization")
public class HospitalizationController {

    private final IMediator mediator;

    public HospitalizationController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateHospitalizationMessage> createAllergy(@RequestBody CreateHospitalizationRequest request) {
        CreateHospitalizationCommand createCommand = CreateHospitalizationCommand.fromRequest(request);
        CreateHospitalizationMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateHospitalizationRequest request) {

        UpdateHospitalizationCommand command = UpdateHospitalizationCommand.fromRequest(request, id);
        UpdateHospitalizationMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteHospitalizationCommand command = new DeleteHospitalizationCommand(id);
        DeleteHospitalizationMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdHospitalizationQuery query = new GetByIdHospitalizationQuery(id);
        HospitalizationResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchHospitalizationQuery query = new GetSearchHospitalizationQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
