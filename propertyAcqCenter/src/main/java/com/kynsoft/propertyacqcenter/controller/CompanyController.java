package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.company.create.CreateCompanyCommand;
import com.kynsoft.propertyacqcenter.application.command.company.create.CreateCompanyMessage;
import com.kynsoft.propertyacqcenter.application.command.company.create.CreateCompanyRequest;
import com.kynsoft.propertyacqcenter.application.command.company.delete.DeleteCompanyCommand;
import com.kynsoft.propertyacqcenter.application.command.company.delete.DeleteCompanyMessage;
import com.kynsoft.propertyacqcenter.application.command.company.update.UpdateCompanyCommand;
import com.kynsoft.propertyacqcenter.application.command.company.update.UpdateCompanyMessage;
import com.kynsoft.propertyacqcenter.application.command.company.update.UpdateCompanyRequest;
import com.kynsoft.propertyacqcenter.application.query.company.getById.GetByIdContactPersonQuery;
import com.kynsoft.propertyacqcenter.application.query.company.search.GetSearchContactPersonQuery;
import com.kynsoft.propertyacqcenter.application.response.CompanyResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final IMediator mediator;

    public CompanyController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateCompanyRequest request) {
        CreateCompanyCommand createCommand = CreateCompanyCommand.fromRequest(request);
        CreateCompanyMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateCompanyRequest request) {

        UpdateCompanyCommand command = UpdateCompanyCommand.fromRequest(id, request);
        UpdateCompanyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteCompanyCommand command = new DeleteCompanyCommand(id);
        DeleteCompanyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdContactPersonQuery query = new GetByIdContactPersonQuery(id);
        CompanyResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchContactPersonQuery query = new GetSearchContactPersonQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }
}
