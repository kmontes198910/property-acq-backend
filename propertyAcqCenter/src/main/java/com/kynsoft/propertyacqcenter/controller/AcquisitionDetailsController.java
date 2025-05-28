package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.create.CreateAcquisitionDetailsCommand;
import com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.create.CreateAcquisitionDetailsMessage;
import com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.create.CreateAcquisitionDetailsRequest;
import com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.delete.DeleteAcquisitionDetailsCommand;
import com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.delete.DeleteAcquisitionDetailsMessage;
import com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.update.UpdateAcquisitionDetailsCommand;
import com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.update.UpdateAcquisitionDetailsMessage;
import com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.update.UpdateAcquisitionDetailsRequest;
import com.kynsoft.propertyacqcenter.application.query.acquisitionDetails.getById.GetByIdAcquisitionDetailsQuery;
import com.kynsoft.propertyacqcenter.application.query.acquisitionDetails.search.GetSearchAcquisitionDetailsQuery;
import com.kynsoft.propertyacqcenter.application.response.AcquisitionDetailsResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acquisition-details")
public class AcquisitionDetailsController {

    private final IMediator mediator;

    public AcquisitionDetailsController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> createAllergy(@RequestBody CreateAcquisitionDetailsRequest request) {
        CreateAcquisitionDetailsCommand createCommand = CreateAcquisitionDetailsCommand.fromRequest(request);
        CreateAcquisitionDetailsMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateAcquisitionDetailsRequest request) {

        UpdateAcquisitionDetailsCommand command = UpdateAcquisitionDetailsCommand.fromRequest(request, id);
        UpdateAcquisitionDetailsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteAcquisitionDetailsCommand command = new DeleteAcquisitionDetailsCommand(id);
        DeleteAcquisitionDetailsMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdAcquisitionDetailsQuery query = new GetByIdAcquisitionDetailsQuery(id);
        AcquisitionDetailsResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchAcquisitionDetailsQuery query = new GetSearchAcquisitionDetailsQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
