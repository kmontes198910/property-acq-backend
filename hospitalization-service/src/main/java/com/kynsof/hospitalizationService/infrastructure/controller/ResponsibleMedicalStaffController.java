package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.create.CreateResponsibleMedicalStaffCommand;
import com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.create.CreateResponsibleMedicalStaffMessage;
import com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.create.CreateResponsibleMedicalStaffRequest;
import com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.delete.DeleteResponsibleMedicalStaffCommand;
import com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.delete.DeleteResponsibleMedicalStaffMessage;
import com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.update.UpdateResponsibleMedicalStaffCommand;
import com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.update.UpdateResponsibleMedicalStaffMessage;
import com.kynsof.hospitalizationService.application.command.responsibleMedicalStaff.update.UpdateResponsibleMedicalStaffRequest;
import com.kynsof.hospitalizationService.application.query.responsibleMedicalStaff.getById.GetByIdResponsibleMedicalStaffQuery;
import com.kynsof.hospitalizationService.application.query.responsibleMedicalStaff.search.GetSearchResponsibleMedicalStaffQuery;
import com.kynsof.hospitalizationService.application.response.ResponsibleMedicalStaffResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/responsible-medical-staff")
public class ResponsibleMedicalStaffController {

    private final IMediator mediator;

    public ResponsibleMedicalStaffController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateResponsibleMedicalStaffMessage> createAllergy(@RequestBody CreateResponsibleMedicalStaffRequest request) {
        CreateResponsibleMedicalStaffCommand createCommand = CreateResponsibleMedicalStaffCommand.fromRequest(request);
        CreateResponsibleMedicalStaffMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateResponsibleMedicalStaffRequest request) {

        UpdateResponsibleMedicalStaffCommand command = UpdateResponsibleMedicalStaffCommand.fromRequest(request, id);
        UpdateResponsibleMedicalStaffMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteResponsibleMedicalStaffCommand command = new DeleteResponsibleMedicalStaffCommand(id);
        DeleteResponsibleMedicalStaffMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdResponsibleMedicalStaffQuery query = new GetByIdResponsibleMedicalStaffQuery(id);
        ResponsibleMedicalStaffResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchResponsibleMedicalStaffQuery query = new GetSearchResponsibleMedicalStaffQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
