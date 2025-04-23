package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.companyType.create.CreateCompanyTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.companyType.create.CreateCompanyTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.companyType.create.CreateCompanyTypeRequest;
import com.kynsoft.propertyacqcenter.application.command.companyType.delete.DeleteCompanyTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.companyType.delete.DeleteCompanyTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.companyType.update.UpdateCompanyTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.companyType.update.UpdateCompanyTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.companyType.update.UpdateCompanyTypeRequest;
import com.kynsoft.propertyacqcenter.application.query.companyType.getById.GetByIdCompanyTypeQuery;
import com.kynsoft.propertyacqcenter.application.query.companyType.search.GetSearchCompanyTypeQuery;
import com.kynsoft.propertyacqcenter.application.response.CompanyTypeResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company-type")
public class CompanyTypeController {

    private final IMediator mediator;

    public CompanyTypeController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateCompanyTypeMessage> createAllergy(@RequestBody CreateCompanyTypeRequest request) {
        CreateCompanyTypeCommand createCommand = CreateCompanyTypeCommand.fromRequest(request);
        CreateCompanyTypeMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateCompanyTypeRequest request) {

        UpdateCompanyTypeCommand command = UpdateCompanyTypeCommand.fromRequest(request, id);
        UpdateCompanyTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteCompanyTypeCommand command = new DeleteCompanyTypeCommand(id);
        DeleteCompanyTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdCompanyTypeQuery query = new GetByIdCompanyTypeQuery(id);
        CompanyTypeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchCompanyTypeQuery query = new GetSearchCompanyTypeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
