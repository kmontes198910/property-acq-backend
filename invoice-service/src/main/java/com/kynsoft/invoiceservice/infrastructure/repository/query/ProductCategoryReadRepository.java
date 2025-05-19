package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.Customer;
import com.kynsoft.invoiceservice.infrastructure.entities.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para consultas de categorías de productos
 */
@Repository
public interface ProductCategoryReadRepository extends JpaRepository<ProductCategory, UUID>, JpaSpecificationExecutor<ProductCategory> {
    
    /**
     * Busca una categoría por su nombre exacto
     */
    Optional<ProductCategory> findByName(String name);
    
    /**
     * Busca categorías que contengan el nombre especificado (búsqueda parcial e insensible a mayúsculas/minúsculas)
     */
    List<ProductCategory> findByNameContainingIgnoreCase(String name);
    
    /**
     * Encuentra todas las categorías activas
     */
    List<ProductCategory> findByStatusTrue();
    Page<ProductCategory> findAll(Specification specification, Pageable pageable);
}
