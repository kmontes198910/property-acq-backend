package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.query.business.getbyid.GetBusinessByIdQuery;
import com.kynsoft.cirugia.application.response.BusinessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/business")
public class BusinessController {

    private final IMediator mediator;

    public BusinessController(IMediator mediator) {

        this.mediator = mediator;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BusinessResponse> getByIdHttpReplicate(@PathVariable UUID id) {

        GetBusinessByIdQuery query = new GetBusinessByIdQuery(id);
        BusinessResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

}
