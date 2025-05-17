package com.kynsoft.invoiceservice.domain.service.impl;

import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceIssuerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceIssuerService implements IInvoiceIssuerService {

    private final InvoiceIssuerRepository invoiceIssuerRepository;

    @Override
    @Transactional(readOnly = true)
    public InvoiceIssuerDto getById(UUID id) {
        log.info("Buscando emisor de facturas con ID: {}", id);
        
        InvoiceIssuer issuer = invoiceIssuerRepository.findById(id)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.ISSUER_NOT_FOUND, 
                        "Emisor de facturas no encontrado con ID: " + id));
        
        return InvoiceIssuerDto.fromEntity(issuer);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceIssuerDto getByRuc(String ruc) {
        log.info("Buscando emisor de facturas con RUC: {}", ruc);
        
        InvoiceIssuer issuer = invoiceIssuerRepository.findByRuc(ruc)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.ISSUER_NOT_FOUND, 
                        "Emisor de facturas no encontrado con RUC: " + ruc));
        
        return InvoiceIssuerDto.fromEntity(issuer);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceIssuerDto getActiveIssuer() {
        log.info("Buscando emisor de facturas activo");
        
        InvoiceIssuer issuer = invoiceIssuerRepository.findFirstByStatusTrue()
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.ISSUER_NOT_FOUND, 
                        "No se encontró un emisor de facturas activo"));
        
        return InvoiceIssuerDto.fromEntity(issuer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceIssuerDto> getAll() {
        log.info("Obteniendo todos los emisores de facturas");
        return invoiceIssuerRepository.findAll().stream()
                .map(InvoiceIssuerDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public com.kynsof.share.core.domain.response.PaginatedResponse search(
            org.springframework.data.domain.Pageable pageable, 
            java.util.List<com.kynsof.share.core.domain.request.FilterCriteria> filterCriteria) {
        
        log.info("Realizando búsqueda avanzada de emisores de facturas con filtros y paginación");
        
        com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder<InvoiceIssuer> specifications = 
            new com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder<>(filterCriteria);
        
        // Ejecutar la consulta con paginación
        org.springframework.data.domain.Page<InvoiceIssuer> page = invoiceIssuerRepository.findAll(specifications, pageable);
        
        // Convertir los resultados a DTOs
        List<InvoiceIssuerDto> issuerDtos = page.getContent().stream()
                .map(InvoiceIssuerDto::fromEntity)
                .collect(Collectors.toList());
        
        // Construir y devolver la respuesta paginada
        return new com.kynsof.share.core.domain.response.PaginatedResponse(
                issuerDtos,                // data
                page.getTotalPages(),        // totalPages
                page.getNumberOfElements(),  // totalElementsPage
                page.getTotalElements(),     // totalElements
                page.getSize(),              // size
                page.getNumber()             // page
        );
    }
}
