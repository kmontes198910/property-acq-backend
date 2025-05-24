package com.kynsoft.medicaltest.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.medicaltest.application.command.labtestparameter.create.CreateLabTestParameterCommand;
import com.kynsoft.medicaltest.application.command.labtestparameter.create.CreateLabTestParameterMessage;
import com.kynsoft.medicaltest.application.command.labtestparameter.create.CreateLabTestParameterRequest;
import com.kynsoft.medicaltest.application.command.labtestparameter.update.UpdateLabTestParameterCommand;
import com.kynsoft.medicaltest.application.command.labtestparameter.update.UpdateLabTestParameterMessage;
import com.kynsoft.medicaltest.application.command.labtestparameter.update.UpdateLabTestParameterRequest;
import com.kynsoft.medicaltest.application.command.labtestparameter.delete.DeleteLabTestParameterCommand;
import com.kynsoft.medicaltest.application.command.labtestparameter.delete.DeleteLabTestParameterMessage;
import com.kynsoft.medicaltest.application.query.labtestparameter.getbyid.GetLabTestParameterByIdQuery;
import com.kynsoft.medicaltest.application.query.labtestparameter.getbyid.LabTestParameterResponse;
import com.kynsoft.medicaltest.application.query.labtestparameter.getbylabtest.GetLabTestParametersByLabTestIdQuery;
import com.kynsoft.medicaltest.application.query.labtestparameter.search.SearchLabTestParameterQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST para gestionar parámetros de exámenes de laboratorio (LabTestParameter)
 */
@RestController
@RequestMapping("/api/lab-test-parameters")
@RequiredArgsConstructor
public class LabTestParameterController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    /**
     * Obtiene un parámetro de examen de laboratorio por su ID
     *
     * @param id El ID del parámetro
     * @return ResponseEntity con el parámetro encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        LabTestParameterResponse response = mediator.send(new GetLabTestParameterByIdQuery(id));
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene todos los parámetros de un examen de laboratorio
     *
     * @param labTestId El ID del examen de laboratorio
     * @return ResponseEntity con la lista de parámetros
     */
    @GetMapping("/by-lab-test/{labTestId}")
    public ResponseEntity<?> getByLabTestId(@PathVariable UUID labTestId) {
        List<LabTestParameterResponse> response = mediator.send(new GetLabTestParametersByLabTestIdQuery(labTestId));
        return ResponseEntity.ok(response);
    }

    /**
     * Crea un nuevo parámetro de examen de laboratorio
     *
     * @param request La solicitud con los datos del nuevo parámetro
     * @param userId ID del usuario que realiza la acción
     * @return ResponseEntity con el mensaje de confirmación
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateLabTestParameterRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        CreateLabTestParameterCommand command = CreateLabTestParameterCommand.fromRequest(request, UUID.fromString(userId));

        CreateLabTestParameterMessage response = mediator.send(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un parámetro de examen de laboratorio existente
     *
     * @param id El ID del parámetro a actualizar
     * @param request La solicitud con los datos actualizados
     * @param userId ID del usuario que realiza la acción
     * @return ResponseEntity con el mensaje de confirmación
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, 
                                   @RequestBody UpdateLabTestParameterRequest request,
                                   @RequestHeader(value = USER_ID_HEADER) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        UpdateLabTestParameterCommand command = UpdateLabTestParameterCommand.fromRequest(request, id, UUID.fromString(userId));
        UpdateLabTestParameterMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un parámetro de examen de laboratorio
     *
     * @param id El ID del parámetro a eliminar
     * @return ResponseEntity con el mensaje de confirmación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        DeleteLabTestParameterMessage response = mediator.send(new DeleteLabTestParameterCommand(id));
        return ResponseEntity.ok(response);
    }
    
    /**
     * Busca parámetros de exámenes de laboratorio con filtros
     *
     * @param request La solicitud con los criterios de búsqueda
     * @return ResponseEntity con los resultados paginados
     */
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        SearchLabTestParameterQuery query = new SearchLabTestParameterQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}
