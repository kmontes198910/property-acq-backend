package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.medicalStaff.create.CreateMedicalStaffCommand;
import com.kynsof.hospitalizationService.application.command.medicalStaff.create.CreateMedicalStaffMessage;
import com.kynsof.hospitalizationService.application.command.medicalStaff.create.CreateMedicalStaffRequest;
import com.kynsof.hospitalizationService.application.command.medicalStaff.delete.DeleteMedicalStaffCommand;
import com.kynsof.hospitalizationService.application.command.medicalStaff.delete.DeleteMedicalStaffMessage;
import com.kynsof.hospitalizationService.application.command.medicalStaff.update.UpdateMedicalStaffCommand;
import com.kynsof.hospitalizationService.application.command.medicalStaff.update.UpdateMedicalStaffMessage;
import com.kynsof.hospitalizationService.application.command.medicalStaff.update.UpdateMedicalStaffRequest;
import com.kynsof.hospitalizationService.application.query.medicalStaff.getById.GetByIdMedicalStaffQuery;
import com.kynsof.hospitalizationService.application.query.medicalStaff.search.GetSearchMedicalStaffQuery;
import com.kynsof.hospitalizationService.application.response.MedicalStaffResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medical-staff")
public class MedicalStaffController {

    private final IMediator mediator;

    public MedicalStaffController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateMedicalStaffMessage> createAllergy(@RequestBody CreateMedicalStaffRequest request) {
        CreateMedicalStaffCommand createCommand = CreateMedicalStaffCommand.fromRequest(request);
        CreateMedicalStaffMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateMedicalStaffRequest request) {

        UpdateMedicalStaffCommand command = UpdateMedicalStaffCommand.fromRequest(request, id);
        UpdateMedicalStaffMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteMedicalStaffCommand command = new DeleteMedicalStaffCommand(id);
        DeleteMedicalStaffMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdMedicalStaffQuery query = new GetByIdMedicalStaffQuery(id);
        MedicalStaffResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchMedicalStaffQuery query = new GetSearchMedicalStaffQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
