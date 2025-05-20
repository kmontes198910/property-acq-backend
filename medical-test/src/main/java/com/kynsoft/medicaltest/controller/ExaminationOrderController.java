package com.kynsoft.medicaltest.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.medicaltest.application.command.examinationorder.addexamination.AddExaminationToOrderCommand;
import com.kynsoft.medicaltest.application.command.examinationorder.addexamination.AddExaminationToOrderMessage;
import com.kynsoft.medicaltest.application.command.examinationorder.create.CreateExaminationOrderCommand;
import com.kynsoft.medicaltest.application.command.examinationorder.create.CreateExaminationOrderMessage;
import com.kynsoft.medicaltest.application.command.examinationorder.create.CreateExaminationOrderRequest;
import com.kynsoft.medicaltest.application.command.examinationorder.removeexamination.RemoveExaminationFromOrderCommand;
import com.kynsoft.medicaltest.application.command.examinationorder.removeexamination.RemoveExaminationFromOrderMessage;
import com.kynsoft.medicaltest.application.command.examinationorder.update.UpdateExaminationOrderCommand;
import com.kynsoft.medicaltest.application.command.examinationorder.update.UpdateExaminationOrderMessage;
import com.kynsoft.medicaltest.application.command.examinationorder.update.UpdateExaminationOrderRequest;
import com.kynsoft.medicaltest.application.query.examinationorder.ExaminationOrderListResponse;
import com.kynsoft.medicaltest.application.query.examinationorder.ExaminationOrderResponse;
import com.kynsoft.medicaltest.application.query.examinationorder.getbyid.GetExaminationOrderByIdQuery;
import com.kynsoft.medicaltest.application.query.examinationorder.list.ListExaminationOrdersQuery;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Órdenes de Exámenes", description = "API para gestionar órdenes de exámenes médicos")
public class ExaminationOrderController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-Id";

    @Operation(summary = "Crear orden", description = "Crea una nueva orden de exámenes médicos")
    @PostMapping
    public ResponseEntity<UUID> createOrder(
            @Valid @RequestBody CreateExaminationOrderRequest request,
            HttpServletRequest httpRequest) {

        String userId = httpRequest.getHeader(USER_ID_HEADER);
        CreateExaminationOrderCommand command = CreateExaminationOrderCommand.fromRequest(request, userId);
        CreateExaminationOrderMessage message = mediator.send(command);
        return new ResponseEntity<>(message.getId(), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener orden por ID", description = "Obtiene una orden de exámenes por su identificador único")
    @GetMapping("/{id}")
    public ResponseEntity<ExaminationOrderResponse> getOrderById(@PathVariable UUID id) {
        GetExaminationOrderByIdQuery query = new GetExaminationOrderByIdQuery(id);
        ExaminationOrderResponse response = mediator.send(query);
        return response != null
                ? new ResponseEntity<>(response, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Actualizar orden", description = "Actualiza una orden de exámenes existente")
    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateOrder(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateExaminationOrderRequest request,
            HttpServletRequest httpRequest) {

        String userId = httpRequest.getHeader(USER_ID_HEADER);
        UpdateExaminationOrderCommand command = UpdateExaminationOrderCommand.fromRequest(id, request, userId);
        UpdateExaminationOrderMessage message = mediator.send(command);
        return new ResponseEntity<>(message.getId(), HttpStatus.OK);
    }

    @Operation(summary = "Listar órdenes", description = "Lista órdenes de exámenes según filtros opcionales")
    @GetMapping
    public ResponseEntity<ExaminationOrderListResponse> listOrders(
            @RequestParam(required = false) UUID patientId,
            @RequestParam(required = false) UUID doctorId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID businessId) {

        ListExaminationOrdersQuery query;

        if (patientId != null) {
            query = ListExaminationOrdersQuery.byPatientId(patientId);
        } else if (doctorId != null) {
            query = ListExaminationOrdersQuery.byDoctorId(doctorId);
        } else if (status != null) {
            query = ListExaminationOrdersQuery.byStatus(status);
        } else if (businessId != null) {
            query = ListExaminationOrdersQuery.byBusinessId(businessId);
        } else {
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate = endDate.minusDays(1);
            query = ListExaminationOrdersQuery.byDateRange(startDate, endDate);
        }

        ExaminationOrderListResponse response = mediator.send(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar orden", description = "Elimina una orden de exámenes por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @PathVariable UUID id,
            HttpServletRequest httpRequest) {

        GetExaminationOrderByIdQuery query = new GetExaminationOrderByIdQuery(id);
        ExaminationOrderResponse response = mediator.send(query);

        if (response == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // En un futuro se podría implementar el DeleteExaminationOrderCommand
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Agregar examen a orden", description = "Agrega un examen existente a una orden")
    @PutMapping("/{orderId}/examinations/{examinationId}")
    public ResponseEntity<UUID> addExaminationToOrder(
            @PathVariable UUID orderId,
            @PathVariable UUID examinationId,
            HttpServletRequest httpRequest) {

        String userId = httpRequest.getHeader(USER_ID_HEADER);
        AddExaminationToOrderCommand command = AddExaminationToOrderCommand.create(orderId, examinationId, userId);
        AddExaminationToOrderMessage message = mediator.send(command);
        return new ResponseEntity<>(message.getOrderId(), HttpStatus.OK);
    }

    @Operation(summary = "Eliminar examen de orden", description = "Elimina un examen de una orden de exámenes")
    @DeleteMapping("/{orderId}/examinations/{examinationId}")
    public ResponseEntity<UUID> removeExaminationFromOrder(
            @PathVariable UUID orderId,
            @PathVariable UUID examinationId,
            HttpServletRequest httpRequest) {

        String userId = httpRequest.getHeader(USER_ID_HEADER);
        RemoveExaminationFromOrderCommand command = RemoveExaminationFromOrderCommand.create(orderId, examinationId, userId);
        RemoveExaminationFromOrderMessage message = mediator.send(command);
        return new ResponseEntity<>(message.getOrderId(), HttpStatus.OK);
    }
}