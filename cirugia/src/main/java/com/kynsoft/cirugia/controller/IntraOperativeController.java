package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.intraOperative.create.CreateIntraOperativeCommand;
import com.kynsoft.cirugia.application.command.intraOperative.create.CreateIntraOperativeMessage;
import com.kynsoft.cirugia.application.command.intraOperative.create.CreateIntraOperativeRequest;
import com.kynsoft.cirugia.application.command.intraOperative.update.UpdateIntraOperativeCommand;
import com.kynsoft.cirugia.application.command.intraOperative.update.UpdateIntraOperativeMessage;
import com.kynsoft.cirugia.application.command.intraOperative.update.UpdateIntraOperativeRequest;
import com.kynsoft.cirugia.application.query.intraoperative.getBySurgeryId.GetIntraOperativeBySurgeryIdQuery;
import com.kynsoft.cirugia.application.query.intraoperative.getBySurgeryId.GetIntraOperativeBySurgeryIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/intraoperatives")
@Tag(name = "IntraOperative", description = "Endpoints para gestionar registros intraoperatorios")
public class IntraOperativeController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    public IntraOperativeController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo registro intraoperatorio", description = "Crea un nuevo registro intraoperatorio asociado a una cirugía")
    public ResponseEntity<?> create(@RequestBody CreateIntraOperativeRequest request,
                                    @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
                                    @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        log.info("Creating new IntraOperative for surgery ID: {}", request.getSurgeryId());
        CreateIntraOperativeCommand command = CreateIntraOperativeCommand.fromRequest(request, UUID.fromString(userId));
        CreateIntraOperativeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);

    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Actualizar un registro intraoperatorio", description = "Actualiza los datos de un registro intraoperatorio existente")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateIntraOperativeRequest request,
                                    @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
                                    @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        log.info("Updating IntraOperative with ID: {}", id);
        UpdateIntraOperativeCommand command = UpdateIntraOperativeCommand.fromRequest(request, id, UUID.fromString(userId));
        UpdateIntraOperativeMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/surgery/{surgeryId}")
    @Operation(summary = "Obtener registros intraoperatorios por ID de cirugía", description = "Obtiene todos los registros intraoperatorios asociados a una cirugía específica")
    public ResponseEntity<?> getBySurgeryId(@PathVariable("surgeryId") UUID surgeryId) {
        log.info("Fetching IntraOperative records for surgery ID: {}", surgeryId);
        GetIntraOperativeBySurgeryIdQuery query = new GetIntraOperativeBySurgeryIdQuery(surgeryId);
        GetIntraOperativeBySurgeryIdResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}