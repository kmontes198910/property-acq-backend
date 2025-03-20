package com.kynsof.evaluation.controller;

import com.kynsof.evaluation.application.query.doctor.getByIdHttp.FindHttpDoctorByIdQuery;
import com.kynsof.share.core.domain.http.entity.DoctorHttp;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final IMediator mediator;

    public DoctorController(IMediator mediator) {

        this.mediator = mediator;
    }

    @GetMapping(path = "/http/replicate/{id}")
    public ResponseEntity<DoctorHttp> getByIdHttpReplicate(@PathVariable UUID id) {

        FindHttpDoctorByIdQuery query = new FindHttpDoctorByIdQuery(id);
        DoctorHttp response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

}
