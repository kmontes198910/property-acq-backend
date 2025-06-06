package com.kynsoft.settings.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.settings.application.command.address.create.CreateAddressCommand;
import com.kynsoft.settings.application.command.address.create.CreateAddressMessage;
import com.kynsoft.settings.application.command.address.create.CreateAddressRequest;
import com.kynsoft.settings.application.command.address.delete.DeleteAddressCommand;
import com.kynsoft.settings.application.command.address.delete.DeleteAddressMessage;
import com.kynsoft.settings.application.command.address.update.UpdateAddressCommand;
import com.kynsoft.settings.application.command.address.update.UpdateAddressMessage;
import com.kynsoft.settings.application.command.address.update.UpdateAddressRequest;
import com.kynsoft.settings.application.query.address.getById.GetByIdAddressQuery;
import com.kynsoft.settings.application.query.address.search.GetSearchAddressQuery;
import com.kynsoft.settings.application.response.AddressResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final IMediator mediator;

    public AddressController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateAddressMessage> createAllergy(@RequestBody CreateAddressRequest request) {
        CreateAddressCommand createCommand = CreateAddressCommand.fromRequest(request);
        CreateAddressMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateAddressRequest request) {

        UpdateAddressCommand command = UpdateAddressCommand.fromRequest(request, id);
        UpdateAddressMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteAddressCommand command = new DeleteAddressCommand(id);
        DeleteAddressMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdAddressQuery query = new GetByIdAddressQuery(id);
        AddressResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchAddressQuery query = new GetSearchAddressQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
