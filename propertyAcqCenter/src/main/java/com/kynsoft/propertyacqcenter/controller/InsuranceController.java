package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.insurance.create.CreateInsuranceCommand;
import com.kynsoft.propertyacqcenter.application.command.insurance.create.CreateInsuranceMessage;
import com.kynsoft.propertyacqcenter.application.command.insurance.create.CreateInsuranceRequest;
import com.kynsoft.propertyacqcenter.application.command.insurance.delete.DeleteInsuranceCommand;
import com.kynsoft.propertyacqcenter.application.command.insurance.delete.DeleteInsuranceMessage;
import com.kynsoft.propertyacqcenter.application.command.insurance.update.UpdateInsuranceCommand;
import com.kynsoft.propertyacqcenter.application.command.insurance.update.UpdateInsuranceMessage;
import com.kynsoft.propertyacqcenter.application.command.insurance.update.UpdateInsuranceRequest;
import com.kynsoft.propertyacqcenter.application.query.insurance.getById.FindByIdInsuranceQuery;
import com.kynsoft.propertyacqcenter.application.query.insurance.search.GetSearchInsuranceQuery;
import com.kynsoft.propertyacqcenter.application.response.InsuranceResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insurance")
public class InsuranceController {

    private final IMediator mediator;

    public InsuranceController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateInsuranceMessage> createAllergy(@RequestBody CreateInsuranceRequest request) {
        CreateInsuranceCommand createCommand = CreateInsuranceCommand.fromRequest(request);
        CreateInsuranceMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateInsuranceRequest request) {

        UpdateInsuranceCommand command = UpdateInsuranceCommand.fromRequest(request, id);
        UpdateInsuranceMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteInsuranceCommand command = new DeleteInsuranceCommand(id);
        DeleteInsuranceMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindByIdInsuranceQuery query = new FindByIdInsuranceQuery(id);
        InsuranceResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchInsuranceQuery query = new GetSearchInsuranceQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
