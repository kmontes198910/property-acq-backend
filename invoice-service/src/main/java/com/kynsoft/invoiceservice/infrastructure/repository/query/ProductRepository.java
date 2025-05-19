package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByMainCode(String mainCode);
    
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:searchTerm% OR p.mainCode LIKE %:searchTerm% OR p.description LIKE %:searchTerm%")
    List<Product> search(String searchTerm);
    
    List<Product> findByCategoryId(UUID categoryId);
}