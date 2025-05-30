package com.kynsoft.medicaltest.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.medicaltest.application.query.doctor.getbyid.DoctorResponse;
import com.kynsoft.medicaltest.application.query.doctor.getbyid.GetDoctorByIdQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final IMediator mediator;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        DoctorResponse response = mediator.send(new GetDoctorByIdQuery(id));
        return ResponseEntity.ok(response);
    }
}
