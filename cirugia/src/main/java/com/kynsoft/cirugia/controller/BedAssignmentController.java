package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.interfaces.IBedAssignmentService;
import com.kynsoft.cirugia.domain.model.BedAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bed-assignments")
@RequiredArgsConstructor
public class BedAssignmentController {

    private final IBedAssignmentService bedAssignmentService;

    @GetMapping("/{id}")
    public ResponseEntity<BedAssignment> getById(@PathVariable UUID id) {
        return bedAssignmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<BedAssignment>> findByPatientId(@PathVariable UUID patientId) {
        List<BedAssignment> assignments = bedAssignmentService.findByPatientId(patientId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<List<BedAssignment>> findBySurgeryId(@PathVariable UUID surgeryId) {
        List<BedAssignment> assignments = bedAssignmentService.findBySurgeryId(surgeryId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/bed/{bedId}")
    public ResponseEntity<List<BedAssignment>> findByBedId(@PathVariable UUID bedId) {
        List<BedAssignment> assignments = bedAssignmentService.findByBedId(bedId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/business/{businessId}")
    public ResponseEntity<List<BedAssignment>> findByBusinessId(@PathVariable UUID businessId) {
        List<BedAssignment> assignments = bedAssignmentService.findByBusinessId(businessId);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BedAssignment>> findByStatus(@PathVariable String status) {
        List<BedAssignment> assignments = bedAssignmentService.findByStatus(status);
        return ResponseEntity.ok(assignments);
    }

    @GetMapping("/active/{businessId}")
    public ResponseEntity<List<BedAssignment>> findActiveAssignments(@PathVariable UUID businessId) {
        List<BedAssignment> assignments = bedAssignmentService.findActiveAssignments(businessId);
        return ResponseEntity.ok(assignments);
    }

    @PostMapping
    public ResponseEntity<BedAssignment> assignBed(@RequestBody BedAssignment bedAssignment) {
        BedAssignment created = bedAssignmentService.assignBed(bedAssignment);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BedAssignment> update(@PathVariable UUID id, @RequestBody BedAssignment bedAssignment) {
        bedAssignment.setId(id);
        BedAssignment updated = bedAssignmentService.update(bedAssignment);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id, @PathVariable String status) {
        bedAssignmentService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/release")
    public ResponseEntity<Void> releaseBed(@PathVariable UUID id, @RequestParam UUID releasedBy) {
        bedAssignmentService.releaseBed(id, releasedBy);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        List<FilterCriteria> filterCriteria = request.getFilter();
        PaginatedResponse response = bedAssignmentService.search(pageable, filterCriteria);
        return ResponseEntity.ok(response);
    }
}