package com.kynsoft.settings.controller;


import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.settings.application.command.service.create.CreateServiceCommand;
import com.kynsoft.settings.application.command.service.create.CreateServiceMessage;
import com.kynsoft.settings.application.command.service.create.CreateServiceRequest;
import com.kynsoft.settings.application.command.service.delete.ServiceDeleteCommand;
import com.kynsoft.settings.application.command.service.delete.ServiceDeleteMessage;
import com.kynsoft.settings.application.command.service.update.UpdateServiceCommand;
import com.kynsoft.settings.application.command.service.update.UpdateServiceMessage;
import com.kynsoft.settings.application.command.service.update.UpdateServiceRequest;
import com.kynsoft.settings.application.query.service.getbyid.FindServiceByIdQuery;

import com.kynsoft.settings.application.query.service.search.GetSearchServiceQuery;
import com.kynsoft.settings.application.response.ServicesResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    private final IMediator mediator;

    public ServiceController(IMediator mediator){

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateServiceRequest request)  {
        CreateServiceCommand createCommand = CreateServiceCommand.fromRequest(request);
        CreateServiceMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ServicesResponse> getById(@PathVariable UUID id) {

        FindServiceByIdQuery query = new FindServiceByIdQuery(id);
        ServicesResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }



    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchServiceQuery query = new GetSearchServiceQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceDeleteMessage> delete(@PathVariable("id") UUID id) {

        ServiceDeleteCommand command = new ServiceDeleteCommand(id);
        ServiceDeleteMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateServiceMessage> update(@PathVariable("id") UUID id, @RequestBody UpdateServiceRequest request) {

        UpdateServiceCommand command = UpdateServiceCommand.fromRequest(id,request);
        UpdateServiceMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}
