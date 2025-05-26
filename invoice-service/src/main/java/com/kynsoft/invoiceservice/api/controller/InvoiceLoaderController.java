package com.kynsoft.invoiceservice.api.controller;

import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import com.kynsoft.invoiceservice.infrastructure.service.InvoiceLoaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/invoices-loader")
public class InvoiceLoaderController {

    private final InvoiceLoaderService invoiceLoaderService;

    public InvoiceLoaderController(InvoiceLoaderService invoiceLoaderService) {
        this.invoiceLoaderService = invoiceLoaderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getInvoiceById(@PathVariable UUID id) {
        Invoice invoice = invoiceLoaderService.loadCompleteInvoice(id);
        
        if (invoice == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Construir un DTO simplificado para evitar problemas de serialización
        Map<String, Object> response = Map.of(
            "id", invoice.getId(),
            "documentNumber", invoice.getDocumentNumber(),
            "status", invoice.getStatus(),
            "totalAmount", invoice.getTotalAmount(),
            "emissionDate", invoice.getEmissionDate(),
            "detailsCount", invoice.getDetails().size(),
            "detailsWithTaxes", invoice.getDetails().stream()
                .map(detail -> Map.of(
                    "id", detail.getId(),
                    "description", detail.getDescription(),
                    "quantity", detail.getQuantity(),
                    "unitPrice", detail.getUnitPrice(),
                    "taxesCount", detail.getTaxes().size()
                ))
                .collect(Collectors.toList()),
            "paymentsCount", invoice.getPayments().size(),
            "additionalFieldsCount", invoice.getAdditionalFields().size(),
            "taxesCount", invoice.getTaxes().size()
        );
        
        return ResponseEntity.ok(response);
    }
}
