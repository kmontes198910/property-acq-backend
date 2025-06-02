package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.business.create.CreateBusinessCommand;
import com.kynsoft.propertyacqcenter.application.command.business.create.CreateBusinessMessage;
import com.kynsoft.propertyacqcenter.application.command.business.create.CreateBusinessRequest;
import com.kynsoft.propertyacqcenter.application.command.business.delete.DeleteBusinessCommand;
import com.kynsoft.propertyacqcenter.application.command.business.delete.DeleteBusinessMessage;
import com.kynsoft.propertyacqcenter.application.command.business.update.UpdateBusinessCommand;
import com.kynsoft.propertyacqcenter.application.command.business.update.UpdateBusinessMessage;
import com.kynsoft.propertyacqcenter.application.command.business.update.UpdateBusinessRequest;
import com.kynsoft.propertyacqcenter.application.query.business.getById.GetByIdBusinessQuery;
import com.kynsoft.propertyacqcenter.application.query.business.search.GetSearchBusinessQuery;
import com.kynsoft.propertyacqcenter.application.response.BusinessResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final IMediator mediator;

    public BusinessController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateBusinessMessage> createAllergy(@RequestBody CreateBusinessRequest request) {
        CreateBusinessCommand createCommand = CreateBusinessCommand.fromRequest(request);
        CreateBusinessMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateBusinessRequest request) {

        UpdateBusinessCommand command = UpdateBusinessCommand.fromRequest(request, id);
        UpdateBusinessMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteBusinessCommand command = new DeleteBusinessCommand(id);
        DeleteBusinessMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        GetByIdBusinessQuery query = new GetByIdBusinessQuery(id);
        BusinessResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchBusinessQuery query = new GetSearchBusinessQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

}
