package com.kynsof.patients.controller;

import com.kynsof.patients.application.query.dashboard.countPatient.CountPatientQuery;
import com.kynsof.patients.application.query.dashboard.countPatient.CountPatientResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final IMediator mediator;

    public DashboardController(IMediator mediator){

        this.mediator = mediator;
    }


    @GetMapping("/countPatient")
    public ResponseEntity<?> countPatient() {
        try {
            CountPatientQuery query = new CountPatientQuery();
            CountPatientResponse response = mediator.send(query);
            Long count = response.getCountPatient();

            return ResponseEntity.ok().body(Map.of("count", count)); // Devolviendo un JSON estructurado
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error fetching patient count", "details", e.getMessage()));
        }
    }

}