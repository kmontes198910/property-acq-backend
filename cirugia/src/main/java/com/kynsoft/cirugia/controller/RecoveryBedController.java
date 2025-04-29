package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.interfaces.IRecoveryBedService;
import com.kynsoft.cirugia.domain.model.RecoveryBed;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/recovery-beds")
@RequiredArgsConstructor
public class RecoveryBedController {

    private final IRecoveryBedService recoveryBedService;

    @GetMapping("/{id}")
    public ResponseEntity<RecoveryBed> getById(@PathVariable UUID id) {
        return recoveryBedService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<RecoveryBed>> findByBusinessId(@PathVariable UUID businessId) {
        List<RecoveryBed> beds = recoveryBedService.findByBusinessId(businessId);
        return ResponseEntity.ok(beds);
    }

    @GetMapping("/available/{businessId}")
    public ResponseEntity<List<RecoveryBed>> findAvailableBeds(@PathVariable UUID businessId) {
        List<RecoveryBed> beds = recoveryBedService.findAvailableBeds(businessId);
        return ResponseEntity.ok(beds);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RecoveryBed>> findByStatus(@PathVariable String status) {
        List<RecoveryBed> beds = recoveryBedService.findByStatus(status);
        return ResponseEntity.ok(beds);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<RecoveryBed>> findByType(@PathVariable String type) {
        List<RecoveryBed> beds = recoveryBedService.findByType(type);
        return ResponseEntity.ok(beds);
    }

    @PostMapping
    public ResponseEntity<RecoveryBed> create(@RequestBody RecoveryBed recoveryBed) {
        RecoveryBed created = recoveryBedService.create(recoveryBed);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecoveryBed> update(@PathVariable UUID id, @RequestBody RecoveryBed recoveryBed) {
        recoveryBed.setId(id);
        RecoveryBed updated = recoveryBedService.update(recoveryBed);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @PathVariable String status) {
        recoveryBedService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        recoveryBedService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        List<FilterCriteria> filterCriteria = request.getFilter();
        PaginatedResponse response = recoveryBedService.search(pageable, filterCriteria);
        return ResponseEntity.ok(response);
    }
}