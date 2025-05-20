package com.kynsoft.invoiceservice.domain.service.impl;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.invoiceservice.application.query.product.getById.ProductDto;
import com.kynsoft.invoiceservice.domain.service.IProductService;
import com.kynsoft.invoiceservice.infrastructure.entities.Product;
import com.kynsoft.invoiceservice.infrastructure.repository.command.ProductWriteRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductWriteRepository productWriteRepository;

    @Override
    public PaginatedResponse searchAdvanced(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Realizando búsqueda avanzada de productos con filtros y paginación");
        
        GenericSpecificationsBuilder<Product> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        
        // Ejecutar la consulta con paginación
        Page<Product> page = productRepository.findAll(specifications, pageable);
        
        // Convertir los resultados a DTOs
        List<ProductDto> productDtos = page.getContent().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
        
        // Construir y devolver la respuesta paginada
        return new PaginatedResponse(
                productDtos,                  // data
                page.getTotalPages(),         // totalPages
                page.getNumberOfElements(),   // totalElementsPage
                page.getTotalElements(),      // totalElements
                page.getSize(),               // size
                page.getNumber()              // page
        );
    }
    
    @Override
    public void delete(UUID id) {
        log.info("Realizando eliminación lógica del producto con ID: {}", id);
        
        Product product = productWriteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        // Realizar eliminación lógica (cambiar estado a inactivo)
        product.setStatus(false);
        
        // Guardar los cambios
        productWriteRepository.save(product);
        
        log.info("Producto eliminado lógicamente de forma exitosa: {}", id);
    }
    
    /**
     * Mapea una entidad Product a un DTO ProductDto
     * @param product Entidad Product a convertir
     * @return ProductDto con los datos mapeados
     */
    private ProductDto mapEntityToDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .mainCode(product.getMainCode())
                .auxiliarCode(product.getAuxiliaryCode())
                .description(product.getDescription())
                .unitPrice(product.getPrice())
                .stock(product.getStock())
                .taxCode(product.getTaxCode())
                .taxPercentage(product.getTaxPercentage())
                .isService(product.getIsService())
                .isActive(product.getStatus())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }
}
