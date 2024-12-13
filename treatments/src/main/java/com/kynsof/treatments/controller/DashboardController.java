package com.kynsof.treatments.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsof.treatments.application.query.dashboard.contConsultByYears.ContConsultByYearsQuery;
import com.kynsof.treatments.application.query.dashboard.contConsultByYears.ContConsultByYearsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final IMediator mediator;

    public DashboardController(IMediator mediator){

        this.mediator = mediator;
    }

    /**
     * Endpoint para obtener la cantidad de consultas por mes, filtrando por empresa y año.
     *
     * @param businessId El ID de la empresa.
     * @param year El año para el que se desean los datos.
     * @return Lista de conteos por mes (indexados de 0 a 11 para enero a diciembre).
     */
    @GetMapping("/external-consultation-count-by-month")
    public ResponseEntity<?> getConsultationsCountByMonth(
            @RequestParam UUID businessId,
            @RequestParam int year) {

        ContConsultByYearsQuery query = new ContConsultByYearsQuery(businessId, year);
        ContConsultByYearsResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }
}
