package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.insuranceBroker.create.CreateInsuranceBrokerCommand;
import com.kynsoft.propertyacqcenter.application.command.insuranceBroker.create.CreateInsuranceBrokerMessage;
import com.kynsoft.propertyacqcenter.application.command.insuranceBroker.create.CreateInsuranceBrokerRequest;
import com.kynsoft.propertyacqcenter.application.command.insuranceBroker.delete.DeleteInsuranceBrokerCommand;
import com.kynsoft.propertyacqcenter.application.command.insuranceBroker.delete.DeleteInsuranceBrokerMessage;
import com.kynsoft.propertyacqcenter.application.command.insuranceBroker.update.UpdateInsuranceBrokerCommand;
import com.kynsoft.propertyacqcenter.application.command.insuranceBroker.update.UpdateInsuranceBrokerMessage;
import com.kynsoft.propertyacqcenter.application.command.insuranceBroker.update.UpdateInsuranceBrokerRequest;
import com.kynsoft.propertyacqcenter.application.query.insuranceBroker.getById.FindByIdInsuranceBrokerQuery;
import com.kynsoft.propertyacqcenter.application.query.insuranceBroker.search.GetSearchInsuranceBrokerQuery;
import com.kynsoft.propertyacqcenter.application.response.InsuranceBrokerResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insurance-broker")
public class InsuranceBrokerController {

    private final IMediator mediator;

    public InsuranceBrokerController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateInsuranceBrokerRequest request) {
        CreateInsuranceBrokerCommand createCommand = CreateInsuranceBrokerCommand.fromRequest(request);
        CreateInsuranceBrokerMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateInsuranceBrokerRequest request) {

        UpdateInsuranceBrokerCommand command = UpdateInsuranceBrokerCommand.fromRequest(request, id);
        UpdateInsuranceBrokerMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteInsuranceBrokerCommand command = new DeleteInsuranceBrokerCommand(id);
        DeleteInsuranceBrokerMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindByIdInsuranceBrokerQuery query = new FindByIdInsuranceBrokerQuery(id);
        InsuranceBrokerResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchInsuranceBrokerQuery query = new GetSearchInsuranceBrokerQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
