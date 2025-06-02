package com.kynsoft.invoiceservice.infrastructure.repository.command;

import com.kynsoft.invoiceservice.infrastructure.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repositorio para operaciones de escritura en categorías de productos
 */
@Repository
public interface ProductCategoryWriteRepository extends JpaRepository<ProductCategory, UUID> {
}
