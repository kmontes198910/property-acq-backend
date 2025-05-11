package com.kynsoft.invoiceservice.controller;

import com.kynsoft.invoiceservice.application.services.InvoiceService;
import com.kynsoft.invoiceservice.dto.FacturaRequestDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Facturas", description = "API para la gestión de facturas electrónicas")
public class InvoiceController {

    private final InvoiceService invoiceService;

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
    public ResponseEntity<FacturaResponseDTO> generarFactura(
            @Parameter(description = "Datos de la factura a generar", required = true) 
            @RequestBody FacturaRequestDTO request) {
        log.info("Recibida solicitud para generar factura");
        
        // Ya no es necesario generar el secuencial aquí, ahora se maneja en el servicio
        // utilizando la secuencia del emisor
        
        // Invocar al servicio para generar y guardar la factura
        FacturaResponseDTO response = invoiceService.generateInvoice(request);
        
        if ("ERROR".equals(response.getEstado())) {
            log.error("Error al generar factura: {}", response.getMensaje());
            return ResponseEntity.badRequest().body(response);
        }
        
        log.info("Factura generada correctamente con clave de acceso: {}", response.getClaveAcceso());
        return ResponseEntity.ok(response);
    }
}