package com.kynsoft.invoiceservice.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.invoiceservice.application.command.invoiceIssuer.create.CreateInvoiceIssuerCommand;
import com.kynsoft.invoiceservice.application.command.invoiceIssuer.create.CreateInvoiceIssuerMessage;
import com.kynsoft.invoiceservice.application.command.invoiceIssuer.create.CreateInvoiceIssuerRequest;
import com.kynsoft.invoiceservice.application.command.invoiceIssuer.update.UpdateInvoiceIssuerCommand;
import com.kynsoft.invoiceservice.application.command.invoiceIssuer.update.UpdateInvoiceIssuerMessage;
import com.kynsoft.invoiceservice.application.command.invoiceIssuer.update.UpdateInvoiceIssuerRequest;
import com.kynsoft.invoiceservice.application.query.invoiceIssuer.getById.GetInvoiceIssuerByIdQuery;
import com.kynsoft.invoiceservice.application.query.invoiceIssuer.getById.InvoiceIssuerResponse;
import com.kynsoft.invoiceservice.application.query.invoiceIssuer.search.SearchInvoiceIssuerAdvancedQuery;
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
@RequestMapping("/api/invoice-issuers")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Emisores de Facturas", description = "API para la gestión de emisores de facturas")
public class InvoiceIssuerController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo emisor de facturas", 
               description = "Registra un nuevo emisor de facturas en el sistema")
    @ApiResponse(responseCode = "201", 
                 description = "Emisor creado correctamente",
                 content = @Content(mediaType = "application/json", 
                                   schema = @Schema(implementation = CreateInvoiceIssuerMessage.class)))
    public ResponseEntity<CreateInvoiceIssuerMessage> createIssuer(
            @Parameter(description = "Datos del emisor a crear", required = true) 
            @RequestBody CreateInvoiceIssuerRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Creating new invoice issuer: {}", request.getBusinessName());
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        CreateInvoiceIssuerCommand command = CreateInvoiceIssuerCommand.fromRequest(request, userUuid);
        CreateInvoiceIssuerMessage response = mediator.send(command);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar un emisor de facturas", 
               description = "Actualiza los datos de un emisor de facturas existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Emisor actualizado correctamente",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = UpdateInvoiceIssuerMessage.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Emisor no encontrado",
                     content = @Content)
    })
    public ResponseEntity<UpdateInvoiceIssuerMessage> updateIssuer(
            @Parameter(description = "ID del emisor a actualizar", required = true) 
            @PathVariable UUID id,
            @Parameter(description = "Datos actualizados del emisor", required = true) 
            @RequestBody UpdateInvoiceIssuerRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        
        log.info("Updating invoice issuer with ID: {}", id);
        
        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        UpdateInvoiceIssuerCommand command = UpdateInvoiceIssuerCommand.fromRequest(request, id, userUuid);
        UpdateInvoiceIssuerMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener emisor por ID", 
               description = "Recupera un emisor de facturas específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Emisor encontrado",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = InvoiceIssuerResponse.class))),
        @ApiResponse(responseCode = "404", 
                     description = "Emisor no encontrado",
                     content = @Content)
    })
    public ResponseEntity<InvoiceIssuerResponse> getIssuerById(
            @Parameter(description = "ID del emisor a buscar", required = true) 
            @PathVariable UUID id) {
        
        log.info("Fetching invoice issuer with ID: {}", id);
        
        GetInvoiceIssuerByIdQuery query = new GetInvoiceIssuerByIdQuery(id);
        InvoiceIssuerResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/search")
    @Operation(summary = "Búsqueda avanzada de emisores de facturas", 
               description = "Busca emisores de facturas con filtros avanzados y paginación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                     description = "Búsqueda exitosa",
                     content = @Content(mediaType = "application/json", 
                                       schema = @Schema(implementation = PaginatedResponse.class)))
    })
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        log.info("Realizando búsqueda avanzada de emisores de facturas");
        
        Pageable pageable = PageableUtil.createPageable(request);
        SearchInvoiceIssuerAdvancedQuery query = new SearchInvoiceIssuerAdvancedQuery(
                pageable, 
                request.getFilter(), 
                request.getQuery()
        );
        
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}