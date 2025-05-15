package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.bedassignment.create.CreateBedAssignmentCommand;
import com.kynsoft.cirugia.application.command.bedassignment.create.CreateBedAssignmentMessage;
import com.kynsoft.cirugia.application.command.bedassignment.create.CreateBedAssignmentRequest;
import com.kynsoft.cirugia.application.query.bedassignment.FindBedAssignmentsBySurgeryQuery;
import com.kynsoft.cirugia.application.query.bedassignment.FindBedAssignmentsBySurgeryQueryResult;
import com.kynsoft.cirugia.application.query.bedassignment.FindLastActiveBedAssignmentByBedIdQuery;
import com.kynsoft.cirugia.application.query.bedassignment.FindLastActiveBedAssignmentByBedIdQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bed-assignments")
@RequiredArgsConstructor
public class BedAssignmentController {

    private final IMediator mediator;
    
    /**
     * Crea una nueva asignación de cama gestionando automáticamente asignaciones previas
     * Libera la cama anterior y asigna la nueva cama
     * 
     * @param request Datos de la asignación
     * @param userId ID del usuario que realiza la acción
     * @return La asignación creada
     */
    @PostMapping("")
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
    
    /**
     * Encuentra la última asignación activa para una cama específica
     * 
     * @param bedId ID de la cama
     * @param businessId ID del negocio (opcional)
     * @return La última asignación con estado ASSIGNED para la cama
     */
    @GetMapping("/bed/{bedId}/active")
    public ResponseEntity<?> findLastActiveAssignmentByBedId(
            @PathVariable UUID bedId,
            @RequestParam(required = false) UUID businessId) {
        
        FindLastActiveBedAssignmentByBedIdQuery query = new FindLastActiveBedAssignmentByBedIdQuery(bedId, businessId);
        FindLastActiveBedAssignmentByBedIdQueryResult result = mediator.send(query);
        
        if (result == null || result.getBedAssignment() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró ninguna asignación activa para la cama con ID: " + bedId);
        }
        
        return ResponseEntity.ok(result);
    }
}