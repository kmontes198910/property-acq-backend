package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    
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
    @EntityGraph(attributePaths = {
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
    @EntityGraph(attributePaths = {
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
     * @return Lista de facturas en el estado especificado con todas sus relaciones cargadas
     */
    @EntityGraph(attributePaths = {
            "issuer",
            "customer",
            "details"
    })
    List<Invoice> findByStatus(InvoiceStatus status);
    
    /**
     * Busca facturas por estado con todas las colecciones cargadas de forma segura
     * @param status Estado de la factura
     * @return Lista de facturas en el estado especificado con sus relacionas principales
     */
    @Query("SELECT DISTINCT i FROM Invoice i " +
           "LEFT JOIN FETCH i.issuer " +
           "LEFT JOIN FETCH i.customer " +
           "LEFT JOIN FETCH i.details d " +
           "WHERE i.status = :status")
    List<Invoice> findByStatusWithDetails(@Param("status") InvoiceStatus status);

    /**
     * Busca facturas por múltiples criterios
     * @param customerId ID del cliente (opcional)
     * @param documentNumber Número de documento (opcional)
     * @param status Estado de la factura (opcional)
     * @return Lista de facturas que cumplen con los criterios de búsqueda con todas sus relaciones cargadas
     */
    @EntityGraph(attributePaths = {
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
}