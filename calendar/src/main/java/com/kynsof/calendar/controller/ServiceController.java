package com.kynsof.calendar.controller;

import com.kynsof.calendar.application.command.service.create.CreateServiceCommand;
import com.kynsof.calendar.application.command.service.create.CreateServiceMessage;
import com.kynsof.calendar.application.command.service.create.CreateServiceRequest;
import com.kynsof.calendar.application.command.service.delete.ServiceDeleteCommand;
import com.kynsof.calendar.application.command.service.delete.ServiceDeleteMessage;
import com.kynsof.calendar.application.command.service.update.UpdateServiceCommand;
import com.kynsof.calendar.application.command.service.update.UpdateServiceMessage;
import com.kynsof.calendar.application.command.service.update.UpdateServiceRequest;
import com.kynsof.calendar.application.query.businessService.GetServicesSimpleByBusinessId.FindServiceSimpleByIdBusinessQuery;
import com.kynsof.calendar.application.query.service.ServicesResponse;
import com.kynsof.calendar.application.query.service.getbyid.FindServiceByIdQuery;
import com.kynsof.calendar.application.query.service.getservicesbyidresource.FindServicesByIdResourceQuery;
import com.kynsof.calendar.application.query.service.search.GetSearchServiceQuery;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.data.domain.PageRequest;
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

    @GetMapping(path = "/services-by-resource/{id}")
    public ResponseEntity<?> findServicesByResourceId(@PathVariable UUID id) {

        Pageable pageable = PageRequest.of(0, 1000);
        FindServicesByIdResourceQuery query = new FindServicesByIdResourceQuery(id, pageable);
        PaginatedResponse response = mediator.send(query);

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
    public ResponseEntity<UpdateServiceMessage> update(@PathVariable("id") UUID id,@RequestBody UpdateServiceRequest request) {

        UpdateServiceCommand command = UpdateServiceCommand.fromRequest(id,request);
        UpdateServiceMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/business/{businessId}")
    public ResponseEntity<?> findServicesByBusinessId(
            @PathVariable UUID businessId,
            @RequestParam(required = false) String serviceName) {

        Pageable pageable = PageRequest.of(0, 1000);
        FindServiceSimpleByIdBusinessQuery query = new FindServiceSimpleByIdBusinessQuery(businessId, pageable, serviceName);
        PaginatedResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }
}
