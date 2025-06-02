package com.kynsof.treatments.controller;

import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/lab-orders")
@RequiredArgsConstructor
public class LabOrderController {

    private final IExternalConsultationService externalConsultationService;

    @GetMapping
    public ResponseEntity<PaginatedResponse> getAllLabOrders(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) UUID paciente,
            @RequestParam(required = false) UUID doctor,
            @RequestParam(required = false) UUID resource) {
        
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("consultationTime").descending());
        PaginatedResponse response = externalConsultationService.findAllLabOrders(pageable, paciente, doctor, resource);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<PaginatedResponse> getLabOrdersByBusiness(
            @PathVariable UUID businessId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) UUID paciente,
            @RequestParam(required = false) UUID doctor,
            @RequestParam(required = false) UUID resource) {
        
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("consultationTime").descending());
        PaginatedResponse response = externalConsultationService.findAllLabOrdersByBusiness(pageable, businessId, paciente, doctor, resource);
        return ResponseEntity.ok(response);
    }
}