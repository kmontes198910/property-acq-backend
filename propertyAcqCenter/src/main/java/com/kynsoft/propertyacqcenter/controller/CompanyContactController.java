package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.companyContact.create.CreateCompanyContactCommand;
import com.kynsoft.propertyacqcenter.application.command.companyContact.create.CreateCompanyContactMessage;
import com.kynsoft.propertyacqcenter.application.command.companyContact.create.CreateCompanyContactRequest;
import com.kynsoft.propertyacqcenter.application.command.companyContact.delete.DeleteCompanyContactCommand;
import com.kynsoft.propertyacqcenter.application.command.companyContact.delete.DeleteCompanyContactMessage;
import com.kynsoft.propertyacqcenter.application.command.companyContact.update.UpdateCompanyContactCommand;
import com.kynsoft.propertyacqcenter.application.command.companyContact.update.UpdateCompanyContactMessage;
import com.kynsoft.propertyacqcenter.application.command.companyContact.update.UpdateCompanyContactRequest;
import com.kynsoft.propertyacqcenter.application.query.companyContact.getById.GetByIdCompanyContactQuery;
import com.kynsoft.propertyacqcenter.application.query.companyContact.search.GetSearchCompanyContactQuery;
import com.kynsoft.propertyacqcenter.application.response.CompanyContactResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/company-contact")
public class CompanyContactController {

    private final IMediator mediator;

    public CompanyContactController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateCompanyContactMessage> createAllergy(@RequestBody CreateCompanyContactRequest request) {
        CreateCompanyContactCommand createCommand = CreateCompanyContactCommand.fromRequest(request);
        CreateCompanyContactMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateCompanyContactRequest request) {

        UpdateCompanyContactCommand command = UpdateCompanyContactCommand.fromRequest(request, id);
        UpdateCompanyContactMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteCompanyContactCommand command = new DeleteCompanyContactCommand(id);
        DeleteCompanyContactMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdCompanyContactQuery query = new GetByIdCompanyContactQuery(id);
        CompanyContactResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchCompanyContactQuery query = new GetSearchCompanyContactQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
