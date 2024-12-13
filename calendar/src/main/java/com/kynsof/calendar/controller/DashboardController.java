package com.kynsof.calendar.controller;

import com.kynsof.calendar.application.query.dashboard.CountAppointmentsByStatusForBusiness.CountAppointmentsByStatusForBusinessQuery;
import com.kynsof.calendar.application.query.dashboard.CountAppointmentsByStatusForBusiness.CountAppointmentsByStatusForBusinessResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final IMediator mediator;

    public DashboardController(IMediator mediator) {

        this.mediator = mediator;
    }

    @GetMapping(path = "/receipt-count-by-type/{business}")
    public ResponseEntity<?> getUserCountByType(@PathVariable UUID business) {

        CountAppointmentsByStatusForBusinessQuery query = new CountAppointmentsByStatusForBusinessQuery(business);
        CountAppointmentsByStatusForBusinessResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }


}
