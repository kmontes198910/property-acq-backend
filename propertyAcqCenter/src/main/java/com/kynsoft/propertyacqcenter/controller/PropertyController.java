package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.property.create.CreatePropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.property.create.CreatePropertyMessage;
import com.kynsoft.propertyacqcenter.application.command.property.create.CreatePropertyRequest;
import com.kynsoft.propertyacqcenter.application.command.property.delete.DeletePropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.property.delete.DeletePropertyMessage;
import com.kynsoft.propertyacqcenter.application.command.property.update.UpdatePropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.property.update.UpdatePropertyMessage;
import com.kynsoft.propertyacqcenter.application.command.property.update.UpdatePropertyRequest;
import com.kynsoft.propertyacqcenter.application.query.property.getById.GetByIdPropertyQuery;
import com.kynsoft.propertyacqcenter.application.query.property.search.GetSearchPropertyQuery;
import com.kynsoft.propertyacqcenter.application.response.PropertiesResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property")
public class PropertyController {

    private final IMediator mediator;

    public PropertyController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreatePropertyMessage> createAllergy(@RequestBody CreatePropertyRequest request) {
        CreatePropertyCommand createCommand = CreatePropertyCommand.fromRequest(request);
        CreatePropertyMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody UpdatePropertyRequest request) {

        UpdatePropertyCommand command = UpdatePropertyCommand.fromRequest(request, id);
        UpdatePropertyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {

        DeletePropertyCommand command = new DeletePropertyCommand(id);
        DeletePropertyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {

        GetByIdPropertyQuery query = new GetByIdPropertyQuery(id);
        PropertiesResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchPropertyQuery query = new GetSearchPropertyQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
