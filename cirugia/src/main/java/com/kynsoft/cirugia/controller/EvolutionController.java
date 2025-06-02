package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.evolution.create.CreateEvolutionCommand;
import com.kynsoft.cirugia.application.command.evolution.create.CreateEvolutionMessage;
import com.kynsoft.cirugia.application.command.evolution.create.CreateEvolutionRequest;
import com.kynsoft.cirugia.application.command.evolution.update.UpdateEvolutionCommand;
import com.kynsoft.cirugia.application.command.evolution.update.UpdateEvolutionMessage;
import com.kynsoft.cirugia.application.command.evolution.update.UpdateEvolutionRequest;
import com.kynsoft.cirugia.application.command.evolution.delete.DeleteEvolutionCommand;
import com.kynsoft.cirugia.application.command.evolution.delete.DeleteEvolutionMessage;
import com.kynsoft.cirugia.application.query.evolution.getbyid.GetEvolutionByIdQuery;
import com.kynsoft.cirugia.application.query.evolution.getbyid.EvolutionResponse;
import com.kynsoft.cirugia.application.query.evolution.getbysurgeryid.GetEvolutionsBySurgeryIdQuery;
import com.kynsoft.cirugia.application.query.evolution.getbysurgeryid.EvolutionsListResponse;
import com.kynsoft.cirugia.application.query.evolution.search.SearchEvolutionsQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/evolutions")
@RequiredArgsConstructor
public class EvolutionController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        log.info("Obteniendo evolución con ID: {}", id);
        GetEvolutionByIdQuery query = new GetEvolutionByIdQuery(id);
        EvolutionResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<EvolutionsListResponse> findBySurgeryId(@PathVariable UUID surgeryId) {
        log.info("Obteniendo evoluciones para la cirugía ID: {}", surgeryId);
        GetEvolutionsBySurgeryIdQuery query = new GetEvolutionsBySurgeryIdQuery(surgeryId);
        EvolutionsListResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody CreateEvolutionRequest request,
                                   @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
                                   @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        log.info("Creando nueva evolución para la cirugía ID: {}", request.getSurgeryId());
        CreateEvolutionCommand command = CreateEvolutionCommand.fromRequest(request, userId);
        CreateEvolutionMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, 
                                    @RequestBody UpdateEvolutionRequest request,
                                    @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
                                    @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        log.info("Actualizando evolución con ID: {}", id);
        UpdateEvolutionCommand command = UpdateEvolutionCommand.fromRequest(request, id, userId);
        UpdateEvolutionMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        log.info("Eliminando evolución con ID: {}", id);
        DeleteEvolutionCommand command = new DeleteEvolutionCommand(id);
        DeleteEvolutionMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        log.info("Buscando evoluciones con filtros: {}", request.getFilter());
        Pageable pageable = PageableUtil.createPageable(request);
        List<FilterCriteria> filterCriteria = request.getFilter();
        SearchEvolutionsQuery query = new SearchEvolutionsQuery(pageable, filterCriteria);
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}