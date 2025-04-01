package com.kynsof.hospitalizationService.infrastructure.controller;

import com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.create.CreateHospitalDischargeSummaryCommand;
import com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.create.CreateHospitalDischargeSummaryMessage;
import com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.create.CreateHospitalDischargeSummaryRequest;
import com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.delete.DeleteHospitalDischargeSummaryCommand;
import com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.delete.DeleteHospitalDischargeSummaryMessage;
import com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.update.UpdateHospitalDischargeSummaryCommand;
import com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.update.UpdateHospitalDischargeSummaryMessage;
import com.kynsof.hospitalizationService.application.command.hospitalDischargeSummary.update.UpdateHospitalDischargeSummaryRequest;
import com.kynsof.hospitalizationService.application.query.hospitalDischargeSummary.getById.GetByIdHospitalDischargeSummaryQuery;
import com.kynsof.hospitalizationService.application.query.hospitalDischargeSummary.search.GetSearchHospitalDischargeSummaryQuery;
import com.kynsof.hospitalizationService.application.response.HospitalDischargeSummaryResponse;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hospital-discharge-summary")
public class HospitalDischargeSummaryController {

    private final IMediator mediator;

    public HospitalDischargeSummaryController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateHospitalDischargeSummaryMessage> createAllergy(@RequestBody CreateHospitalDischargeSummaryRequest request) {
        CreateHospitalDischargeSummaryCommand createCommand = CreateHospitalDischargeSummaryCommand.fromRequest(request);
        CreateHospitalDischargeSummaryMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateHospitalDischargeSummaryRequest request) {

        UpdateHospitalDischargeSummaryCommand command = UpdateHospitalDischargeSummaryCommand.fromRequest(request, id);
        UpdateHospitalDischargeSummaryMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteHospitalDischargeSummaryCommand command = new DeleteHospitalDischargeSummaryCommand(id);
        DeleteHospitalDischargeSummaryMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdHospitalDischargeSummaryQuery query = new GetByIdHospitalDischargeSummaryQuery(id);
        HospitalDischargeSummaryResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchHospitalDischargeSummaryQuery query = new GetSearchHospitalDischargeSummaryQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
