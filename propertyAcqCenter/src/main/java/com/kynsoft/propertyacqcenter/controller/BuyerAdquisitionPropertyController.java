package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateAdquisitionPropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateAdquisitionPropertyMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateAdquisitionPropertyRequest;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.delete.DeleteAdquisitionPropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.delete.DeleteAdquisitionPropertyMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.UpdateAdquisitionPropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.UpdateAdquisitionPropertyMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.UpdateAdquisitionPropertyRequest;
import com.kynsoft.propertyacqcenter.application.query.adquisitionProperty.getById.GetByIdAdquisitionPropertyQuery;
import com.kynsoft.propertyacqcenter.application.query.adquisitionProperty.search.GetSearchAdquisitionPropertyQuery;
import com.kynsoft.propertyacqcenter.application.response.AdquisitionPropertyResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer-acquisition-property")
public class BuyerAdquisitionPropertyController {

    private final IMediator mediator;

    public BuyerAdquisitionPropertyController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateAdquisitionPropertyMessage> create(@RequestBody CreateAdquisitionPropertyRequest request) {
        CreateAdquisitionPropertyCommand createCommand = CreateAdquisitionPropertyCommand.fromRequest(request);
        CreateAdquisitionPropertyMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateAdquisitionPropertyRequest request) {

        UpdateAdquisitionPropertyCommand command = UpdateAdquisitionPropertyCommand.fromRequest(request, id);
        UpdateAdquisitionPropertyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteAdquisitionPropertyCommand command = new DeleteAdquisitionPropertyCommand(id);
        DeleteAdquisitionPropertyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdAdquisitionPropertyQuery query = new GetByIdAdquisitionPropertyQuery(id);
        AdquisitionPropertyResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchAdquisitionPropertyQuery query = new GetSearchAdquisitionPropertyQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
