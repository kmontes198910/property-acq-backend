package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.mortgage.create.CreateMortgageCommand;
import com.kynsoft.propertyacqcenter.application.command.mortgage.create.CreateMortgageMessage;
import com.kynsoft.propertyacqcenter.application.command.mortgage.create.CreateMortgageRequest;
import com.kynsoft.propertyacqcenter.application.command.mortgage.delete.DeleteMortgageCommand;
import com.kynsoft.propertyacqcenter.application.command.mortgage.delete.DeleteMortgageMessage;
import com.kynsoft.propertyacqcenter.application.command.mortgage.update.UpdateMortgageCommand;
import com.kynsoft.propertyacqcenter.application.command.mortgage.update.UpdateMortgageMessage;
import com.kynsoft.propertyacqcenter.application.command.mortgage.update.UpdateMortgageRequest;
import com.kynsoft.propertyacqcenter.application.query.mortgage.getById.GetByIdMortgageQuery;
import com.kynsoft.propertyacqcenter.application.query.mortgage.search.GetSearchMortgageQuery;
import com.kynsoft.propertyacqcenter.application.response.MortgageResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mortgage")
public class MortgageController {

    private final IMediator mediator;

    public MortgageController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateMortgageRequest request) {
        CreateMortgageCommand createCommand = CreateMortgageCommand.fromRequest(request);
        CreateMortgageMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateMortgageRequest request) {

        UpdateMortgageCommand command = UpdateMortgageCommand.fromRequest(request, id);
        UpdateMortgageMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteMortgageCommand command = new DeleteMortgageCommand(id);
        DeleteMortgageMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdMortgageQuery query = new GetByIdMortgageQuery(id);
        MortgageResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchMortgageQuery query = new GetSearchMortgageQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
