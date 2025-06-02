package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.companyAddress.create.CreateCompanyAddressCommand;
import com.kynsoft.propertyacqcenter.application.command.companyAddress.create.CreateCompanyAddressMessage;
import com.kynsoft.propertyacqcenter.application.command.companyAddress.create.CreateCompanyAddressRequest;
import com.kynsoft.propertyacqcenter.application.command.companyAddress.delete.DeleteCompanyAddressCommand;
import com.kynsoft.propertyacqcenter.application.command.companyAddress.delete.DeleteCompanyAddressMessage;
import com.kynsoft.propertyacqcenter.application.command.companyAddress.update.UpdateCompanyAddressCommand;
import com.kynsoft.propertyacqcenter.application.command.companyAddress.update.UpdateCompanyAddressMessage;
import com.kynsoft.propertyacqcenter.application.command.companyAddress.update.UpdateCompanyAddressRequest;
import com.kynsoft.propertyacqcenter.application.query.companyAddress.getById.GetByIdCompanyAddressQuery;
import com.kynsoft.propertyacqcenter.application.query.companyAddress.search.GetSearchCompanyAddressQuery;
import com.kynsoft.propertyacqcenter.application.response.CompanyAddressResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company-address")
public class CompanyAddressController {

    private final IMediator mediator;

    public CompanyAddressController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateCompanyAddressMessage> createAllergy(@RequestBody CreateCompanyAddressRequest request) {
        CreateCompanyAddressCommand createCommand = CreateCompanyAddressCommand.fromRequest(request);
        CreateCompanyAddressMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateCompanyAddressRequest request) {

        UpdateCompanyAddressCommand command = UpdateCompanyAddressCommand.fromRequest(request, id);
        UpdateCompanyAddressMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteCompanyAddressCommand command = new DeleteCompanyAddressCommand(id);
        DeleteCompanyAddressMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdCompanyAddressQuery query = new GetByIdCompanyAddressQuery(id);
        CompanyAddressResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchCompanyAddressQuery query = new GetSearchCompanyAddressQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
