package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.anesthesia.create.CreateAnesthesiaCommand;
import com.kynsoft.cirugia.application.command.anesthesia.create.CreateAnesthesiaMessage;
import com.kynsoft.cirugia.application.command.anesthesia.create.CreateAnesthesiaRequest;
import com.kynsoft.cirugia.application.command.anesthesia.update.UpdateAnesthesiaCommand;
import com.kynsoft.cirugia.application.command.anesthesia.update.UpdateAnesthesiaMessage;
import com.kynsoft.cirugia.application.command.anesthesia.update.UpdateAnesthesiaRequest;
import com.kynsoft.cirugia.application.query.anesthesia.getBySurgeryId.GetAnesthesiaBySurgeryIdQuery;
import com.kynsoft.cirugia.application.query.anesthesia.getBySurgeryId.GetAnesthesiaBySurgeryIdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para gestionar las operaciones de anestesia.
 */
@RestController
@RequestMapping("/api/anesthesia")
@Slf4j
public class AnesthesiaController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    public AnesthesiaController(IMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Crea un nuevo registro de anestesia.
     *
     * @param request Datos de la anestesia a crear
     * @param userId ID del usuario que realiza la operación
     * @return Respuesta con el ID de la anestesia creada
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody CreateAnesthesiaRequest request,
                                  @RequestHeader(value = USER_ID_HEADER, required = true) String userId) {
        log.info("Creando nueva Anestesia para la cirugía ID: {}", request.getSurgeryId());
        
        CreateAnesthesiaCommand command = CreateAnesthesiaCommand.fromRequest(request, UUID.fromString(userId));
        CreateAnesthesiaMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza un registro de anestesia existente.
     *
     * @param id ID de la anestesia a actualizar
     * @param request Datos actualizados de la anestesia
     * @param userId ID del usuario que realiza la operación
     * @return Respuesta con el ID de la anestesia actualizada
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> update(@PathVariable("id") String id, 
                                   @RequestBody UpdateAnesthesiaRequest request,
                                   @RequestHeader(value = USER_ID_HEADER, required = true) String userId) {
        log.info("Actualizando Anestesia con ID: {}", id);
        
        request.setId(id); // Aseguramos que el ID en la ruta y en el request coincidan
        UpdateAnesthesiaCommand command = UpdateAnesthesiaCommand.fromRequest(request, UUID.fromString(userId));
        UpdateAnesthesiaMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una anestesia por el ID de la cirugía asociada.
     *
     * @param surgeryId ID de la cirugía
     * @return La anestesia asociada a la cirugía, o 404 si no existe
     */
    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<?> getBySurgeryId(@PathVariable("surgeryId") UUID surgeryId) {
        log.info("Obteniendo Anestesia para la Cirugía ID: {}", surgeryId);
        
        GetAnesthesiaBySurgeryIdQuery query = new GetAnesthesiaBySurgeryIdQuery(surgeryId);
        GetAnesthesiaBySurgeryIdResponse response = mediator.send(query);
        if (response == null) {
            log.warn("No se encontró Anestesia para la Cirugía ID: {}", surgeryId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Anestesia no encontrada");
        }
        return ResponseEntity.ok(response);
    }
}