package com.kynsoft.invoiceservice.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.invoiceservice.application.command.invoice.generate.GenerateInvoiceCommand;
import com.kynsoft.invoiceservice.application.command.invoice.generate.GenerateInvoiceMessage;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.GenerateInvoiceRequest;
import com.kynsoft.invoiceservice.application.command.invoice.update.UpdateInvoiceCommand;
import com.kynsoft.invoiceservice.application.command.invoice.update.UpdateInvoiceMessage;
import com.kynsoft.invoiceservice.application.command.invoice.update.request.UpdateInvoiceRequest;
import com.kynsoft.invoiceservice.application.query.invoice.getById.GetInvoiceByIdQuery;
import com.kynsoft.invoiceservice.application.query.invoice.getById.InvoiceResponse;
import com.kynsoft.invoiceservice.application.query.invoice.search.SearchInvoiceAdvancedQuery;
import com.kynsoft.invoiceservice.domain.dto.FacturaResponseDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Facturas", description = "API para la gestión de facturas electrónicas")
public class InvoiceController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @Operation(summary = "Generar factura electrónica",
            description = "Crea y genera una nueva factura electrónica según los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Factura generada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaResponseDTO.class))),
            @ApiResponse(responseCode = "400",
                    description = "Error en la generación de la factura",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FacturaResponseDTO.class)))
    })
    @PostMapping("/generate")
    public ResponseEntity<?> generarFactura(
            @Parameter(description = "Datos de la factura a generar", required = true)
            @RequestBody GenerateInvoiceRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId) {
        log.info("Recibida solicitud para generar factura");

        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        // Ya no es necesario generar el secuencial aquí, ahora se maneja en el servicio
        // utilizando la secuencia del emisor
        GenerateInvoiceCommand command = GenerateInvoiceCommand.fromRequest(request, userUuid);
        // Invocar al servicio para generar y guardar la factura
        GenerateInvoiceMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    @Operation(summary = "Búsqueda avanzada de facturas",
            description = "Busca facturas con filtros avanzados y paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Búsqueda exitosa",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaginatedResponse.class)))
    })
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        log.info("Realizando búsqueda avanzada de facturas");

        Pageable pageable = PageableUtil.createPageable(request);
        SearchInvoiceAdvancedQuery query = new SearchInvoiceAdvancedQuery(
                pageable,
                request.getFilter(),
                request.getQuery()
        );

        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener factura por ID",
            description = "Devuelve los datos completos de una factura específica según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Factura encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InvoiceResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Factura no encontrada",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<InvoiceResponse> getById(
            @Parameter(description = "ID de la factura a consultar", required = true)
            @PathVariable UUID id) {
        log.info("Consultando factura con ID: {}", id);

        GetInvoiceByIdQuery query = new GetInvoiceByIdQuery(id);
        InvoiceResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar factura",
            description = "Actualiza los datos de una factura existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Factura actualizada correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateInvoiceMessage.class))),
            @ApiResponse(responseCode = "404",
                    description = "Factura no encontrada",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400",
                    description = "Error en la actualización de la factura",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> update(
            @Parameter(description = "ID de la factura a actualizar", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Datos de la factura a actualizar", required = true)
            @RequestBody UpdateInvoiceRequest request,
            @RequestHeader(value = USER_ID_HEADER, required = false) String userId,
            @RequestHeader(value = USER_NAME_HEADER, required = false) String userName) {
        log.info("Actualizando factura con ID: {}", id);

        UUID userUuid = userId != null ? UUID.fromString(userId) : null;
        UpdateInvoiceCommand command = UpdateInvoiceCommand.fromRequest(request, id, userUuid);
        UpdateInvoiceMessage response = mediator.send(command);

        return ResponseEntity.ok(response);
    }
}