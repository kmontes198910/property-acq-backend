package com.kynsoft.medicaltest.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.medicaltest.application.command.examination.complete.CompleteExaminationCommand;
import com.kynsoft.medicaltest.application.command.examination.complete.CompleteExaminationMessage;
import com.kynsoft.medicaltest.application.command.examination.create.CreateExaminationCommand;
import com.kynsoft.medicaltest.application.command.examination.create.CreateExaminationMessage;
import com.kynsoft.medicaltest.application.command.examination.create.CreateExaminationRequest;
import com.kynsoft.medicaltest.application.command.examination.update.UpdateExaminationCommand;
import com.kynsoft.medicaltest.application.command.examination.update.UpdateExaminationMessage;
import com.kynsoft.medicaltest.application.command.examination.update.UpdateExaminationRequest;
import com.kynsoft.medicaltest.application.query.examination.ExaminationListResponse;
import com.kynsoft.medicaltest.application.query.examination.ExaminationResponse;
import com.kynsoft.medicaltest.application.query.examination.getbyid.GetExaminationByIdQuery;
import com.kynsoft.medicaltest.application.query.examination.list.ListExaminationsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/examinations")
@RequiredArgsConstructor
@Tag(name = "Exámenes", description = "API para gestionar exámenes médicos")
public class ExaminationController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-Id";

    @Operation(summary = "Crear examen", description = "Crea un nuevo examen médico")
    @PostMapping
    public ResponseEntity<UUID> createExamination(
            @Valid @RequestBody CreateExaminationRequest request,
            HttpServletRequest httpRequest) {

        String userId = httpRequest.getHeader(USER_ID_HEADER);
        CreateExaminationCommand command = CreateExaminationCommand.fromRequest(request, userId);
        CreateExaminationMessage message = mediator.send(command);
        return new ResponseEntity<>(message.getId(), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar examen", description = "Actualiza un examen médico por ID")
    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateExamination(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateExaminationRequest request,
            HttpServletRequest httpRequest) {

        String userId = httpRequest.getHeader(USER_ID_HEADER);
        UpdateExaminationCommand command = UpdateExaminationCommand.fromRequest(id, request, userId);
        UpdateExaminationMessage message = mediator.send(command);
        return new ResponseEntity<>(message.getId(), HttpStatus.OK);
    }

    @Operation(summary = "Obtener examen por ID", description = "Consulta un examen médico por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ExaminationResponse> getExaminationById(@PathVariable UUID id) {
        GetExaminationByIdQuery query = new GetExaminationByIdQuery(id);
        ExaminationResponse response = mediator.send(query);
        return response != null ?
                new ResponseEntity<>(response, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Listar exámenes", description = "Lista exámenes médicos según filtros opcionales")
    @GetMapping
    public ResponseEntity<ExaminationListResponse> listExaminations(
            @RequestParam(required = false) UUID orderId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status) {

        ListExaminationsQuery query;

        if (orderId != null) {
            query = ListExaminationsQuery.byOrderId(orderId);
        } else if (type != null) {
            query = ListExaminationsQuery.byType(type);
        } else if (status != null) {
            query = ListExaminationsQuery.byStatus(status);
        } else {
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = endDate.minusDays(1);
            query = ListExaminationsQuery.byDateRange(startDate, endDate);
        }

        ExaminationListResponse response = mediator.send(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar examen", description = "Elimina un examen médico por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamination(
            @PathVariable UUID id,
            HttpServletRequest httpRequest) {

        GetExaminationByIdQuery query = new GetExaminationByIdQuery(id);
        ExaminationResponse response = mediator.send(query);

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // En una futura implementación:
        // DeleteExaminationCommand command = new DeleteExaminationCommand(id);
        // mediator.send(command);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Completar examen", description = "Marca un examen como completado con sus resultados")
    @PostMapping("/{id}/complete")
    public ResponseEntity<UUID> completeExamination(
            @PathVariable UUID id,
            @RequestParam String results,
            HttpServletRequest httpRequest) {

        String userId = httpRequest.getHeader(USER_ID_HEADER);
        CompleteExaminationCommand command = CompleteExaminationCommand.fromRequest(id, results, userId);
        CompleteExaminationMessage message = mediator.send(command);
        return new ResponseEntity<>(message.getId(), HttpStatus.OK);
    }
}