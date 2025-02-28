package com.kynsof.payment.controller;

import com.kynsof.payment.application.command.client.create.CreateClientCommand;
import com.kynsof.payment.application.command.client.create.CreateClientMessage;
import com.kynsof.payment.application.command.client.create.CreateClientRequest;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.payment.application.query.client.search.GetSearchClientQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final IMediator mediator;

    public ClientController(IMediator mediator){

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CreateClientRequest request) {
        CreateClientCommand createCommand = CreateClientCommand.fromRequest(request);
        CreateClientMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchClientQuery query = new GetSearchClientQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }
}
