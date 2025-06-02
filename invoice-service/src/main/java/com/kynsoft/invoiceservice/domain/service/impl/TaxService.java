package com.kynsoft.invoiceservice.domain.service.impl;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.invoiceservice.domain.dto.TaxDto;
import com.kynsoft.invoiceservice.domain.service.ITaxService;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax;
import com.kynsoft.invoiceservice.infrastructure.repository.command.TaxWriteRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.TaxReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaxService implements ITaxService {

    private final TaxReadRepository taxReadRepository;
    private final TaxWriteRepository taxWriteRepository;

    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse searchAdvanced(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Realizando búsqueda avanzada de impuestos con filtros y paginación");
        
        GenericSpecificationsBuilder<Tax> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        
        // Ejecutar la consulta con paginación
        Page<Tax> page = taxReadRepository.findAll(specifications, pageable);
        
        // Convertir los resultados a DTOs
        List<TaxDto> taxDtos = page.getContent().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
        // Construir y devolver la respuesta paginada
        return new PaginatedResponse(
                taxDtos,                     // data
                page.getTotalPages(),        // totalPages
                page.getNumberOfElements(),  // totalElementsPage
                page.getTotalElements(),     // totalElements
                page.getSize(),              // size
                page.getNumber()             // page
        );
    }
    
    @Override
    @Transactional(readOnly = true)
    public PaginatedResponse findByTaxType(Tax.TaxType type, Pageable pageable) {
        log.info("Buscando impuestos por tipo: {}", type);
        
        List<Tax> taxes = taxReadRepository.findByTaxTypeAndStatusTrue(type);
        
        // Paginar los resultados manualmente ya que findByTaxTypeAndStatusTrue no devuelve un Page
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), taxes.size());
        
        List<Tax> pagedTaxes = taxes.subList(start, end);
        Page<Tax> page = new PageImpl<>(pagedTaxes, pageable, taxes.size());
        
        // Convertir los resultados a DTOs
        List<TaxDto> taxDtos = page.getContent().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
        // Construir y devolver la respuesta paginada
        return new PaginatedResponse(
                taxDtos,                     // data
                page.getTotalPages(),        // totalPages
                page.getNumberOfElements(),  // totalElementsPage
                page.getTotalElements(),     // totalElements
                page.getSize(),              // size
                page.getNumber()             // page
        );
    }
    
    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("Eliminando impuesto con ID: {}", id);
        
        // Buscar el impuesto por su ID
        Tax tax = taxWriteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Impuesto no encontrado con ID: " + id));
        
        // Realizar eliminación lógica (cambiar el estado a inactivo)
        tax.setStatus(false);
        
        // Guardar los cambios
        taxWriteRepository.save(tax);
        
        log.info("Impuesto con ID: {} eliminado exitosamente", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tax> findByCode(String code) {
        return taxReadRepository.findByCode(code);
    }

    @Override
    @Transactional
    public Tax create(Tax tax) {
        return taxWriteRepository.save(tax);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Tax> findById(UUID taxId) {
        return taxReadRepository.findById(taxId);
    }

    @Override
    @Transactional
    public void update(Tax tax) {
        taxWriteRepository.save(tax);
    }

    /**
     * Mapea una entidad Tax a un DTO TaxDto
     * @param tax Entidad Tax a convertir
     * @return TaxDto con los datos mapeados
     */
    private TaxDto mapEntityToDto(Tax tax) {
        return TaxDto.builder()
                .id(tax.getId())
                .code(tax.getCode())
                .name(tax.getName())
                .description(tax.getDescription())
                .value(tax.getValue())
                .taxType(tax.getTaxType())
                .status(tax.getStatus())
                .isPredetermined(tax.getIsPredetermined())
                .createdAt(tax.getCreatedAt())
                .updatedAt(tax.getUpdatedAt())
                .createdBy(tax.getCreatedBy())
                .updatedBy(tax.getUpdatedBy())
                .build();
    }
}
