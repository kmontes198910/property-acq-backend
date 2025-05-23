package com.kynsoft.medicaltest.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.medicaltest.application.command.labtest.create.CreateLabTestCommand;
import com.kynsoft.medicaltest.application.command.labtest.create.CreateLabTestMessage;
import com.kynsoft.medicaltest.application.command.labtest.create.CreateLabTestRequest;
import com.kynsoft.medicaltest.application.command.labtest.update.UpdateLabTestCommand;
import com.kynsoft.medicaltest.application.command.labtest.update.UpdateLabTestMessage;
import com.kynsoft.medicaltest.application.command.labtest.update.UpdateLabTestRequest;
import com.kynsoft.medicaltest.application.command.labtest.delete.DeleteLabTestCommand;
import com.kynsoft.medicaltest.application.command.labtest.delete.DeleteLabTestMessage;
import com.kynsoft.medicaltest.application.query.labtest.getbyid.GetLabTestByIdQuery;
import com.kynsoft.medicaltest.application.query.labtest.getbyid.LabTestResponse;
import com.kynsoft.medicaltest.application.query.labtest.search.SearchLabTestQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador REST para gestionar exámenes de laboratorio (LabTest)
 */
@RestController
@RequestMapping("/api/lab-tests")
@RequiredArgsConstructor
public class LabTestController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    /**
     * Obtiene un examen de laboratorio por su ID
     *
     * @param id El ID del examen de laboratorio
     * @return ResponseEntity con el examen de laboratorio encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        LabTestResponse response = mediator.send(new GetLabTestByIdQuery(id));
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo examen de laboratorio
     *
     * @param request La solicitud con los datos del nuevo examen de laboratorio
     * @param userId ID del usuario que realiza la acción
     * @return ResponseEntity con el mensaje de confirmación
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateLabTestRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        CreateLabTestCommand command = CreateLabTestCommand.fromRequest(request, UUID.fromString(userId));

        CreateLabTestMessage response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un examen de laboratorio existente
     *
     * @param id El ID del examen de laboratorio a actualizar
     * @param request La solicitud con los datos actualizados
     * @param userId ID del usuario que realiza la acción
     * @return ResponseEntity con el mensaje de confirmación
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, 
                                   @RequestBody UpdateLabTestRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        UpdateLabTestCommand command = UpdateLabTestCommand.fromRequest(request, id, UUID.fromString(userId));
        UpdateLabTestMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un examen de laboratorio
     *
     * @param id El ID del examen de laboratorio a eliminar
     * @return ResponseEntity con el mensaje de confirmación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        DeleteLabTestMessage response = mediator.send(new DeleteLabTestCommand(id));
        return ResponseEntity.ok(response);
    }

    /**
     * Busca exámenes de laboratorio con filtros
     *
     * @param request La solicitud con los criterios de búsqueda
     * @return ResponseEntity con los resultados paginados
     */
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        SearchLabTestQuery query = new SearchLabTestQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}
