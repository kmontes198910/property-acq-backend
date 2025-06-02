package com.kynsoft.invoiceservice.domain.service.impl;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.invoiceservice.application.query.productcategory.get.ProductCategoryDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.IProductCategoryService;
import com.kynsoft.invoiceservice.infrastructure.entities.ProductCategory;
import com.kynsoft.invoiceservice.infrastructure.repository.command.ProductCategoryWriteRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.ProductCategoryReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para la gestión de categorías de productos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductCategoryService implements IProductCategoryService {

    private final ProductCategoryReadRepository productCategoryReadRepository;
    private final ProductCategoryWriteRepository productCategoryWriteRepository;

    @Override
    @Transactional
    public UUID create(ProductCategoryDto categoryDto) {
        log.info("Creando nueva categoría de producto: {}", categoryDto.getName());
        
        // Validar que los campos obligatorios estén presentes
        if (categoryDto.getName() == null || categoryDto.getName().trim().isEmpty()) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_DATA, 
                    "El nombre de la categoría es obligatorio");
        }
        
        // Verificar si ya existe una categoría con el mismo nombre
        if (productCategoryReadRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.ALREADY_EXISTS, 
                "Ya existe una categoría con el nombre: " + categoryDto.getName());
        }
        
        // Asignar un ID si no tiene uno
        if (categoryDto.getId() == null) {
            categoryDto.setId(UUID.randomUUID());
        }
        
        // Asegurar que la categoría esté activa por defecto si no se especifica
        if (categoryDto.getStatus() == null) {
            categoryDto.setStatus(true);
        }
        
        // Crear la entidad ProductCategory y guardarla
        ProductCategory category = ProductCategory.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .status(categoryDto.getStatus())
                .createdBy(categoryDto.getCreatedBy())
                .updatedBy(categoryDto.getUpdatedBy())
                .build();
        
        ProductCategory savedCategory = productCategoryWriteRepository.save(category);
        log.info("Categoría de producto creada con ID: {}", savedCategory.getId());
        
        return savedCategory.getId();
    }

    @Override
    @Transactional
    public void update(ProductCategoryDto categoryDto) {
        log.info("Actualizando categoría de producto con ID: {}", categoryDto.getId());
        
        // Validar que el ID de la categoría no sea nulo
        if (categoryDto.getId() == null) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND, 
                    "El ID de la categoría no puede ser nulo para una actualización");
        }
        
        // Verificar que la categoría exista
        ProductCategory existingCategory = productCategoryReadRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND, 
                        "Categoría no encontrada con ID: " + categoryDto.getId()));
        
        // Verificar si se está cambiando el nombre y si el nuevo ya existe
        if (categoryDto.getName() != null && 
            !existingCategory.getName().equals(categoryDto.getName())) {
            productCategoryReadRepository.findByName(categoryDto.getName())
                .ifPresent(category -> {
                    if (!category.getId().equals(categoryDto.getId())) {
                        throw new BusinessInvoiceException(DomainErrorInvoiceMessage.ALREADY_EXISTS, 
                            "Ya existe una categoría con el nombre: " + categoryDto.getName());
                    }
                });
        }
        
        // Actualizar los campos de la categoría
        if (categoryDto.getName() != null) {
            existingCategory.setName(categoryDto.getName());
        }
        
        if (categoryDto.getDescription() != null) {
            existingCategory.setDescription(categoryDto.getDescription());
        }
        
        if (categoryDto.getStatus() != null) {
            existingCategory.setStatus(categoryDto.getStatus());
        }
        
        // Actualizar el campo de auditoría updatedBy
        if (categoryDto.getUpdatedBy() != null) {
            existingCategory.setUpdatedBy(categoryDto.getUpdatedBy());
        }
        
        // Guardar los cambios
        productCategoryWriteRepository.save(existingCategory);
        log.info("Categoría de producto actualizada correctamente, ID: {}", existingCategory.getId());
    }

    @Override
    public ProductCategoryDto findById(UUID id) {
        log.info("Buscando categoría de producto con ID: {}", id);
        
        ProductCategory category = productCategoryReadRepository.findById(id)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND, 
                        "Categoría no encontrada con ID: " + id));
        
        return mapEntityToDto(category);
    }

    @Override
    public List<ProductCategoryDto> searchByName(String name) {
        log.info("Buscando categorías de producto por nombre: {}", name);
        
        List<ProductCategory> categories = productCategoryReadRepository.findByNameContainingIgnoreCase(name);
        
        return categories.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductCategoryDto> findAllActive() {
        log.info("Obteniendo todas las categorías de producto activas");
        
        List<ProductCategory> categories = productCategoryReadRepository.findByStatusTrue();
        
        return categories.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Realizando búsqueda avanzada de categorías de producto con filtros y paginación");

        GenericSpecificationsBuilder<ProductCategory> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        
        // Ejecutar la consulta con paginación
        Page<ProductCategory> page = productCategoryReadRepository.findAll(specifications, pageable);
        
        // Convertir los resultados a DTOs
        List<ProductCategoryDto> categoryDtos = page.getContent().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
        // Construir y devolver la respuesta paginada usando el constructor
        return new PaginatedResponse(
                categoryDtos,                // data
                page.getTotalPages(),        // totalPages
                page.getNumberOfElements(),  // totalElementsPage
                page.getTotalElements(),     // totalElements
                page.getSize(),              // size
                page.getNumber()             // page
        );
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Eliminando categoría de producto con ID: {}", id);

        // Verificar que la categoría existe antes de eliminar
        ProductCategory category = productCategoryReadRepository.findById(id)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND,
                        "Categoría no encontrada con ID: " + id));

        // Eliminar la categoría
        productCategoryWriteRepository.delete(category);
        log.info("Categoría de producto eliminada correctamente, ID: {}", id);
    }

    private ProductCategoryDto mapEntityToDto(ProductCategory category) {
        return ProductCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .status(category.getStatus())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .createdBy(category.getCreatedBy())
                .updatedBy(category.getUpdatedBy())
                .build();
    }
}
