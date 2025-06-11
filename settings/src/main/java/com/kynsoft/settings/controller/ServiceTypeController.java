package com.kynsoft.settings.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.settings.application.command.serviceType.create.CreateServiceTypeCommand;
import com.kynsoft.settings.application.command.serviceType.create.CreateServiceTypeMessage;
import com.kynsoft.settings.application.command.serviceType.create.CreateServiceTypeRequest;
import com.kynsoft.settings.application.command.serviceType.delete.DeleteServiceTypeCommand;
import com.kynsoft.settings.application.command.serviceType.delete.DeleteServiceTypeMessage;
import com.kynsoft.settings.application.command.serviceType.update.UpdateServiceTypeCommand;
import com.kynsoft.settings.application.command.serviceType.update.UpdateServiceTypeMessage;
import com.kynsoft.settings.application.command.serviceType.update.UpdateServiceTypeRequest;
import com.kynsoft.settings.application.query.serviceType.getbyid.FindServiceTypeByIdQuery;
import com.kynsoft.settings.application.query.serviceType.search.GetSearchServiceTypeQuery;
import com.kynsoft.settings.application.response.ServiceTypeResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/service-type")
public class ServiceTypeController {

    private final IMediator mediator;

    public ServiceTypeController(IMediator mediator){

        this.mediator = mediator;
    }
    @PostMapping("")
    public ResponseEntity<CreateServiceTypeMessage> create(@RequestBody @Valid CreateServiceTypeRequest request)  {
        CreateServiceTypeCommand createCommand = CreateServiceTypeCommand.fromRequest(request);
        CreateServiceTypeMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchServiceTypeQuery query = new GetSearchServiceTypeQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ServiceTypeResponse> getById(@PathVariable UUID id) {

        FindServiceTypeByIdQuery query = new FindServiceTypeByIdQuery(id);
        ServiceTypeResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteServiceTypeMessage> deleteServices(@PathVariable("id") UUID id) {

        DeleteServiceTypeCommand command = new DeleteServiceTypeCommand(id);
        DeleteServiceTypeMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateServiceTypeMessage> update(@PathVariable("id") UUID id, @RequestBody UpdateServiceTypeRequest request) {

        UpdateServiceTypeCommand command = UpdateServiceTypeCommand.fromRequest(id,request);
        UpdateServiceTypeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}
