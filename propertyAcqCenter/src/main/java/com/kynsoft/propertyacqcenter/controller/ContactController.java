package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.contact.create.CreateContactCommand;
import com.kynsoft.propertyacqcenter.application.command.contact.create.CreateContactMessage;
import com.kynsoft.propertyacqcenter.application.command.contact.create.CreateContactRequest;
import com.kynsoft.propertyacqcenter.application.command.contact.delete.DeleteContactCommand;
import com.kynsoft.propertyacqcenter.application.command.contact.delete.DeleteContactMessage;
import com.kynsoft.propertyacqcenter.application.command.contact.update.UpdateContactCommand;
import com.kynsoft.propertyacqcenter.application.command.contact.update.UpdateContactMessage;
import com.kynsoft.propertyacqcenter.application.command.contact.update.UpdateContactRequest;
import com.kynsoft.propertyacqcenter.application.query.contact.getById.GetByIdContactQuery;
import com.kynsoft.propertyacqcenter.application.query.contact.search.GetSearchContactQuery;
import com.kynsoft.propertyacqcenter.application.response.ContactResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final IMediator mediator;

    public ContactController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateContactRequest request) {
        CreateContactCommand createCommand = CreateContactCommand.fromRequest(request);
        CreateContactMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateContactRequest request) {

        UpdateContactCommand command = UpdateContactCommand.fromRequest(id, request);
        UpdateContactMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteContactCommand command = new DeleteContactCommand(id);
        DeleteContactMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdContactQuery query = new GetByIdContactQuery(id);
        ContactResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchContactQuery query = new GetSearchContactQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }
}
