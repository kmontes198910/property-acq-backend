package com.kynsoft.invoiceservice.controller;

import com.kynsoft.invoiceservice.domain.dto.InvoiceDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.IInvoiceService;
import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import com.kynsoft.invoiceservice.infrastructure.job.ProcessInvoice;
import com.kynsoft.invoiceservice.infrastructure.mapper.MapperInvoice;
import ec.e.facturacion.sri.modelo.Factura;
import ec.e.facturacion.sri.pdf.generador.FacturaPDFGenerador;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
private final IInvoiceService invoiceService;
    private final MapperInvoice mapperInvoice;
    @GetMapping(value = "/pdf/{id}")
    public ResponseEntity<byte[]> generateInvoice(@PathVariable UUID id) {
        try {
         Invoice invoiceDto = invoiceService.findByIdToEntity(id);
            ProcessInvoice processInvoice = mapperInvoice.convertToFactura(invoiceDto);
            // Generar el PDF como ByteArrayOutputStream
            ByteArrayOutputStream pdfStream = generatePDFInvoice(processInvoice.getFactura(), processInvoice.getInvoiceLogo());

            // Crear encabezados para la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition
                    .builder("inline")
                    .filename("invoice.pdf")
                    .build());

            return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    private ByteArrayOutputStream generatePDFInvoice(Factura factura, String logoBase64) throws IOException {
        try {
            return FacturaPDFGenerador.generarPDF(factura, logoBase64, "#2D4C80");
        } catch (Exception e) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.GENERAL_ERROR,
                    "Error al generar factura: " + e.getMessage());
        }
    }
}
