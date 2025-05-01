package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.query.doctor.getbyid.GetDoctorByIdQuery;
import com.kynsoft.cirugia.application.response.DoctorResponse;
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

    @GetMapping(path = "/{id}")
    public ResponseEntity<DoctorResponse> getByIdHttpReplicate(@PathVariable UUID id) {

        GetDoctorByIdQuery query = new GetDoctorByIdQuery(id);
        DoctorResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

}
