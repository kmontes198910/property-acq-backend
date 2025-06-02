package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.preOperative.create.CreatePreOperativeCommand;
import com.kynsoft.cirugia.application.command.preOperative.create.CreatePreOperativeMessage;
import com.kynsoft.cirugia.application.command.preOperative.create.CreatePreOperativeRequest;
import com.kynsoft.cirugia.application.command.preOperative.update.UpdatePreOperativeCommand;
import com.kynsoft.cirugia.application.command.preOperative.update.UpdatePreOperativeMessage;
import com.kynsoft.cirugia.application.command.preOperative.update.UpdatePreOperativeRequest;
import com.kynsoft.cirugia.application.query.preoperative.getBySurgeryId.GetPreOperativeBySurgeryIdQuery;
import com.kynsoft.cirugia.application.query.preoperative.getBySurgeryId.GetPreOperativeBySurgeryIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/preoperatives")
@Tag(name = "PreOperative", description = "Endpoints para gestionar registros preoperatorios")
public class PreOperativeController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    public PreOperativeController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo registro preoperatorio", description = "Crea un nuevo registro preoperatorio asociado a una cirugía")
    public ResponseEntity<?> create(@RequestBody CreatePreOperativeRequest request,
                      @RequestHeader(value = USER_ID_HEADER) String userId) {
        log.info("Creating new PreOperative for surgery ID: {}", request.getSurgeryId());
        CreatePreOperativeCommand command = CreatePreOperativeCommand.fromRequest(request, userId);
        CreatePreOperativeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizar un registro preoperatorio", description = "Actualiza los datos de un registro preoperatorio existente")
    public ResponseEntity<?> update(@PathVariable("id") UUID id,
                      @RequestBody UpdatePreOperativeRequest request,
                      @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        log.info("Updating PreOperative with ID: {}", id);
        UpdatePreOperativeCommand command = UpdatePreOperativeCommand.fromRequest(request, id, userId);
        UpdatePreOperativeMessage response =mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/surgery/{surgeryId}")
    @Operation(summary = "Obtener registro preoperatorio por ID de cirugía", description = "Obtiene el registro preoperatorio asociado a una cirugía específica")
    public ResponseEntity<?> getBySurgeryId(@PathVariable("surgeryId") UUID surgeryId) {
        log.info("Fetching PreOperative record for surgery ID: {}", surgeryId);
        GetPreOperativeBySurgeryIdQuery query = new GetPreOperativeBySurgeryIdQuery(surgeryId);
        GetPreOperativeBySurgeryIdResponse response = mediator.send(query);
        return ResponseEntity.ok(response.getPreOperative());
    }
}