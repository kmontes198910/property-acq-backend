package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.contactPerson.create.CreateContactPersonCommand;
import com.kynsoft.propertyacqcenter.application.command.contactPerson.create.CreateContactPersonMessage;
import com.kynsoft.propertyacqcenter.application.command.contactPerson.create.CreateContactPersonRequest;
import com.kynsoft.propertyacqcenter.application.command.contactPerson.delete.DeleteContactPersonCommand;
import com.kynsoft.propertyacqcenter.application.command.contactPerson.delete.DeleteContactPersonMessage;
import com.kynsoft.propertyacqcenter.application.command.contactPerson.update.UpdateContactPersonCommand;
import com.kynsoft.propertyacqcenter.application.command.contactPerson.update.UpdateContactPersonMessage;
import com.kynsoft.propertyacqcenter.application.command.contactPerson.update.UpdateContactPersonRequest;
import com.kynsoft.propertyacqcenter.application.query.contactPerson.getById.GetByIdContactPersonQuery;
import com.kynsoft.propertyacqcenter.application.query.contactPerson.search.GetSearchContactPersonQuery;
import com.kynsoft.propertyacqcenter.application.response.ContactPersonResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contact-person")
public class ContactPersonController {

    private final IMediator mediator;

    public ContactPersonController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateContactPersonRequest request) {
        CreateContactPersonCommand createCommand = CreateContactPersonCommand.fromRequest(request);
        CreateContactPersonMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateContactPersonRequest request) {

        UpdateContactPersonCommand command = UpdateContactPersonCommand.fromRequest(id, request);
        UpdateContactPersonMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteContactPersonCommand command = new DeleteContactPersonCommand(id);
        DeleteContactPersonMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdContactPersonQuery query = new GetByIdContactPersonQuery(id);
        ContactPersonResponse response = mediator.send(query);

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
