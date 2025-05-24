package com.kynsoft.invoiceservice.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.invoiceservice.application.command.tax.create.CreateTaxCommand;
import com.kynsoft.invoiceservice.application.command.tax.create.CreateTaxMessage;
import com.kynsoft.invoiceservice.application.command.tax.create.CreateTaxRequest;
import com.kynsoft.invoiceservice.application.command.tax.delete.DeleteTaxCommand;
import com.kynsoft.invoiceservice.application.command.tax.update.UpdateTaxCommand;
import com.kynsoft.invoiceservice.application.command.tax.update.UpdateTaxMessage;
import com.kynsoft.invoiceservice.application.command.tax.update.UpdateTaxRequest;
import com.kynsoft.invoiceservice.application.query.tax.getById.GetTaxByIdQuery;
import com.kynsoft.invoiceservice.application.query.tax.getById.TaxResponse;
import com.kynsoft.invoiceservice.application.query.tax.search.SearchTaxAdvancedQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/taxes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Impuestos", description = "API para la gestión de impuestos")
public class TaxController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo impuesto", 
               description = "Registra un nuevo impuesto en el sistema")
    @ApiResponse(responseCode = "201", 
                 description = "Impuesto creado correctamente",
                 content = @Content(mediaType = "application/json", 
                                   schema = @Schema(implementation = CreateTaxMessage.class)))
    public ResponseEntity<CreateTaxMessage> createTax(
            @Parameter(description = "Datos del impuesto a crear", required = true) 
            @RequestBody CreateTaxRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Creando nuevo impuesto: {}", request.getName());
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        CreateTaxCommand command = CreateTaxCommand.fromRequest(request, userUuid);
        CreateTaxMessage response = mediator.send(command);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar un impuesto", 
               description = "Actualiza los datos de un impuesto existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Impuesto actualizado correctamente",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = UpdateTaxMessage.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Impuesto no encontrado",
                     content = @Content)
    })
    public ResponseEntity<UpdateTaxMessage> updateTax(
            @Parameter(description = "ID del impuesto a actualizar", required = true) 
            @PathVariable UUID id,
            @Parameter(description = "Datos actualizados del impuesto", required = true) 
            @RequestBody UpdateTaxRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Actualizando impuesto con ID: {}", id);
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        UpdateTaxCommand command = UpdateTaxCommand.fromRequest(request, id, userUuid);
        UpdateTaxMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener impuesto por ID", 
               description = "Recupera un impuesto específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Impuesto encontrado",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = TaxResponse.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Impuesto no encontrado",
                     content = @Content)
    })
    public ResponseEntity<TaxResponse> getTaxById(
            @Parameter(description = "ID del impuesto a buscar", required = true) 
            @PathVariable UUID id) {
        
        log.info("Buscando impuesto con ID: {}", id);
        
        GetTaxByIdQuery query = new GetTaxByIdQuery(id);
        TaxResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/search")
    @Operation(summary = "Búsqueda avanzada de impuestos", 
               description = "Busca impuestos con filtros avanzados y paginación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Búsqueda exitosa",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = PaginatedResponse.class)))
    })
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        log.info("Realizando búsqueda avanzada de impuestos");
        
        Pageable pageable = PageableUtil.createPageable(request);
        SearchTaxAdvancedQuery query = new SearchTaxAdvancedQuery(
                pageable, 
                request.getFilter(), 
                request.getQuery()
        );
        
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un impuesto", 
               description = "Marca un impuesto como inactivo (eliminación lógica)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", 
                     description = "Impuesto eliminado correctamente"),
        @ApiResponse(responseCode = "404", 
                     description = "Impuesto no encontrado",
                     content = @Content)
    })
    public ResponseEntity<Void> deleteTax(
            @Parameter(description = "ID del impuesto a eliminar", required = true) 
            @PathVariable UUID id) {
        
        log.info("Eliminando impuesto con ID: {}", id);
        
        DeleteTaxCommand command = new DeleteTaxCommand(id);
        mediator.send(command);
        
        return ResponseEntity.noContent().build();
    }

}
