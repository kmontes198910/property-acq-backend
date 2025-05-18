package com.kynsoft.invoiceservice.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.invoiceservice.application.command.invoice.generate.GenerateInvoiceCommand;
import com.kynsoft.invoiceservice.application.command.invoice.generate.GenerateInvoiceMessage;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.GenerateInvoiceRequest;
import com.kynsoft.invoiceservice.application.query.invoice.search.SearchInvoiceAdvancedQuery;
import com.kynsoft.invoiceservice.dto.FacturaResponseDTO;
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
@RequestMapping("/api/v1/invoices")
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
        GenerateInvoiceMessage response = mediator.send(
                command
        );

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
}