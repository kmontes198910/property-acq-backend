package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import com.kynsoft.invoiceservice.infrastructure.entities.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceReadRepository extends JpaRepository<Invoice, UUID>, JpaSpecificationExecutor<Invoice> {
    
    /**
     * Busca el último secuencial utilizado en las facturas
     * @return el último secuencial en formato de 9 dígitos
     */
    @Query("SELECT MAX(i.sequential) FROM Invoice i")
    Optional<String> findLastSequential();
    
    /**
     * Busca facturas por ID del cliente
     * @param customerId ID del cliente
     * @return Lista de facturas asociadas al cliente con todas sus relaciones cargadas
     */
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {
            "issuer",
            "customer",
            "details",
            "details.taxes",
            "payments",
            "additionalFields",
            "taxes"
    })
    List<Invoice> findByCustomerId(UUID customerId);
    
    /**
     * Busca facturas que contengan el número de documento especificado
     * @param documentNumber Número de documento (completo o parcial)
     * @return Lista de facturas que coinciden con el criterio con todas sus relaciones cargadas
     */
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {
            "issuer",
            "customer",
            "details",
            "details.taxes",
            "payments",
            "additionalFields",
            "taxes"
    })
    List<Invoice> findByDocumentNumberContaining(String documentNumber);
    
    /**
     * Busca facturas por estado
     * @param status Estado de la factura
     * @return Lista de facturas en el estado especificado
     */
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {
            "issuer",
            "customer",
            "details",
            "details.taxes",
            "payments",
            "additionalFields",
            "taxes"
    })
    List<Invoice> findByStatus(InvoiceStatus status);
    
    /**
     * Busca facturas por múltiples criterios
     * @param customerId ID del cliente (opcional)
     * @param documentNumber Número de documento (opcional)
     * @param status Estado de la factura (opcional)
     * @return Lista de facturas que cumplen con los criterios de búsqueda con todas sus relaciones cargadas
     */
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {
            "issuer",
            "customer",
            "details",
            "details.taxes",
            "payments",
            "additionalFields",
            "taxes"
    })
    @Query("SELECT i FROM Invoice i WHERE " +
           "(:customerId IS NULL OR i.customer.id = :customerId) AND " +
           "(:documentNumber IS NULL OR i.documentNumber LIKE %:documentNumber%) AND " +
           "(:status IS NULL OR i.status = :status)")
    List<Invoice> searchByCriteria(
            @Param("customerId") UUID customerId,
            @Param("documentNumber") String documentNumber,
            @Param("status") InvoiceStatus status);

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {
            "customer"
    })
    Page<Invoice> findAll(Specification specification, Pageable pageable);

    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = {
            "issuer",
            "customer",
            "details",
            "details.taxes",
            "payments",
            "additionalFields",
            "taxes"
    })
    Optional<Invoice> findById(UUID id);


}