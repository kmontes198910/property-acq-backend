package com.kynsoft.invoiceservice.controller;

import com.kynsoft.invoiceservice.application.services.InvoiceService;
import com.kynsoft.invoiceservice.dto.FacturaRequestDTO;
import com.kynsoft.invoiceservice.dto.FacturaResponseDTO;
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
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/generate")
    public ResponseEntity<FacturaResponseDTO> generarFactura(@RequestBody FacturaRequestDTO request) {
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