package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceDetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, UUID> {
    
    /**
     * Obtiene los detalles de factura con sus impuestos cargados
     * @param invoiceId ID de la factura
     * @return Detalles de factura con sus impuestos
     */
    @EntityGraph(attributePaths = {
            "taxes",
            "additionalInfo"
    })
    List<InvoiceDetail> findByInvoiceId(UUID invoiceId);
    
    /**
     * Obtiene los detalles de factura con sus impuestos cargados usando JOIN FETCH
     * @param invoiceId ID de la factura
     * @return Detalles de factura con sus impuestos
     */
    @Query("SELECT DISTINCT d FROM InvoiceDetail d " +
           "LEFT JOIN FETCH d.taxes " +
           "WHERE d.invoice.id = :invoiceId")
    List<InvoiceDetail> findByInvoiceIdWithTaxes(@Param("invoiceId") UUID invoiceId);
}
