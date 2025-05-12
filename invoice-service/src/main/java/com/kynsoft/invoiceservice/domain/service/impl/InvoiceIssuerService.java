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
}
