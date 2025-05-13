package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.bedassignment.create.CreateBedAssignmentCommand;
import com.kynsoft.cirugia.application.command.bedassignment.create.CreateBedAssignmentMessage;
import com.kynsoft.cirugia.application.command.bedassignment.create.CreateBedAssignmentRequest;
import com.kynsoft.cirugia.application.query.bedassignment.FindBedAssignmentsBySurgeryQuery;
import com.kynsoft.cirugia.application.query.bedassignment.FindBedAssignmentsBySurgeryQueryResult;
import com.kynsoft.cirugia.domain.service.IBedAssignmentService;
import com.kynsoft.cirugia.domain.dto.BedAssignment;
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
    private final IMediator mediator;

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
    
    /**
     * Crea una nueva asignación de cama gestionando automáticamente asignaciones previas
     * Libera la cama anterior y asigna la nueva cama
     * 
     * @param request Datos de la asignación
     * @param userId ID del usuario que realiza la acción
     * @return La asignación creada
     */
    @PostMapping("/create-replace")
    public ResponseEntity<CreateBedAssignmentMessage> createAndReplaceAssignment(
            @RequestBody CreateBedAssignmentRequest request,
            @RequestHeader(value = "X-User-ID", required = false) String userId) {
        
        CreateBedAssignmentCommand command = CreateBedAssignmentCommand.fromRequest(request, userId);
        CreateBedAssignmentMessage response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Busca todas las asignaciones para una cirugía específica
     * 
     * @param surgeryId ID de la cirugía
     * @param businessId ID del negocio
     * @return Lista de asignaciones encontradas
     */
    @GetMapping("/surgery-detail/{surgeryId}")
    public ResponseEntity<?> findDetailedBySurgeryId(
            @PathVariable UUID surgeryId,
            @RequestParam(required = false) UUID businessId) {
        
        FindBedAssignmentsBySurgeryQuery query = new FindBedAssignmentsBySurgeryQuery(surgeryId, businessId);
        FindBedAssignmentsBySurgeryQueryResult result = mediator.send(query);
        
        if (result == null || result.getBedAssignments() == null || result.getBedAssignments().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron asignaciones para la cirugía con ID: " + surgeryId);
        }
        
        return ResponseEntity.ok(result);
    }
}