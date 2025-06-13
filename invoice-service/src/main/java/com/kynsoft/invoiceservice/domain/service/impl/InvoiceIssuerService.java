package com.kynsoft.invoiceservice.domain.service.impl;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import com.kynsoft.invoiceservice.infrastructure.entities.Issuer;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuingSequence;
import com.kynsoft.invoiceservice.infrastructure.repository.command.InvoiceIssuerWriteRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceIssuerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    private final InvoiceIssuerWriteRepository invoiceIssuerWriteRepository;

    @Override
    @Transactional(readOnly = true)
    public InvoiceIssuerDto getById(UUID id) {
        log.info("Buscando emisor de facturas con ID: {}", id);
        
        Issuer issuer = invoiceIssuerRepository.findById(id)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.ISSUER_NOT_FOUND, 
                        "Emisor de facturas no encontrado con ID: " + id));
        
        return InvoiceIssuerDto.fromEntity(issuer);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceIssuerDto getByRuc(String ruc) {
        log.info("Buscando emisor de facturas con RUC: {}", ruc);
        
        Issuer issuer = invoiceIssuerRepository.findByRuc(ruc)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.ISSUER_NOT_FOUND, 
                        "Emisor de facturas no encontrado con RUC: " + ruc));
        
        return InvoiceIssuerDto.fromEntity(issuer);
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceIssuerDto getActiveIssuer() {
        log.info("Buscando emisor de facturas activo");
        
        Issuer issuer = invoiceIssuerRepository.findFirstByStatusTrue()
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
       Pageable pageable,
            List<FilterCriteria> filterCriteria) {
        
        log.info("Realizando búsqueda avanzada de emisores de facturas con filtros y paginación");
        

        GenericSpecificationsBuilder<Issuer> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        // Ejecutar la consulta con paginación
        org.springframework.data.domain.Page<Issuer> page = invoiceIssuerRepository.findAll(specifications, pageable);
        
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

    @Override
    @Transactional
    public Issuer create(Issuer issuer) {
        return invoiceIssuerWriteRepository.save(issuer);
    }
    
    @Override
    @Transactional
    public InvoiceIssuerDto updateSequence(UUID issuerId, String documentType, Long newSequentialValue) {
        log.info("Actualizando secuencia para emisor ID: {}, tipo de documento: {}, nuevo valor: {}", 
                issuerId, documentType, newSequentialValue);
        
        // Obtener el emisor desde el repositorio
        Issuer issuer = invoiceIssuerRepository.findById(issuerId)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.ISSUER_NOT_FOUND, 
                        "Emisor de facturas no encontrado con ID: " + issuerId));
        
        // Buscar la secuencia específica para actualizar
        boolean found = false;
        for (InvoiceIssuingSequence sequence : issuer.getSequences()) {
            if (documentType.equals(sequence.getDocumentType()) && Boolean.TRUE.equals(sequence.getIsActive())) {
                sequence.setCurrentSequential(newSequentialValue);
                sequence.setLastUsedDate(java.time.LocalDateTime.now());
                found = true;
                break;
            }
        }
        
        // Si no se encontró la secuencia, lanzar excepción
        if (!found) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.SEQUENCE_NOT_FOUND,
                    "No se encontró una secuencia activa para el tipo de documento: " + documentType);
        }
        
        // Guardar los cambios en la base de datos
        Issuer updatedIssuer = invoiceIssuerWriteRepository.save(issuer);
        log.info("Secuencia actualizada correctamente para emisor ID: {}", issuerId);
        
        // Devolver el DTO actualizado
        return InvoiceIssuerDto.fromEntity(updatedIssuer);
    }
}
