package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.subCompanyType.create.CreateSubCompanyTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.subCompanyType.create.CreateSubCompanyTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.subCompanyType.create.CreateSubCompanyTypeRequest;
import com.kynsoft.propertyacqcenter.application.command.subCompanyType.delete.DeleteSubCompanyTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.subCompanyType.delete.DeleteSubCompanyTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.subCompanyType.update.UpdateSubCompanyTypeCommand;
import com.kynsoft.propertyacqcenter.application.command.subCompanyType.update.UpdateSubCompanyTypeMessage;
import com.kynsoft.propertyacqcenter.application.command.subCompanyType.update.UpdateSubCompanyTypeRequest;
import com.kynsoft.propertyacqcenter.application.query.subCompanyType.getById.GetByIdSubCompanyTypeQuery;
import com.kynsoft.propertyacqcenter.application.query.subCompanyType.search.GetSearchSubCompanyTypeQuery;
import com.kynsoft.propertyacqcenter.application.response.SubCompanyTypeResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sub-company-type")
public class SubCompanyTypeController {

    private final IMediator mediator;

    public SubCompanyTypeController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateSubCompanyTypeMessage> createAllergy(@RequestBody CreateSubCompanyTypeRequest request) {
        CreateSubCompanyTypeCommand createCommand = CreateSubCompanyTypeCommand.fromRequest(request);
        CreateSubCompanyTypeMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateSubCompanyTypeRequest request) {

        UpdateSubCompanyTypeCommand command = UpdateSubCompanyTypeCommand.fromRequest(request, id);
        UpdateSubCompanyTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteSubCompanyTypeCommand command = new DeleteSubCompanyTypeCommand(id);
        DeleteSubCompanyTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdSubCompanyTypeQuery query = new GetByIdSubCompanyTypeQuery(id);
        SubCompanyTypeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchSubCompanyTypeQuery query = new GetSearchSubCompanyTypeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
