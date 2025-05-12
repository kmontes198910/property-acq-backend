package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuingSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceIssuingSequenceRepository extends JpaRepository<InvoiceIssuingSequence, UUID> {
    
    /**
     * Busca secuencias por tipo de documento y emisor
     * 
     * @param documentType Tipo de documento
     * @param issuerId ID del emisor
     * @return Lista de secuencias
     */
    List<InvoiceIssuingSequence> findByDocumentTypeAndInvoiceIssuer_Id(String documentType, UUID issuerId);
    
    /**
     * Busca secuencias activas por tipo de documento y emisor
     * 
     * @param documentType Tipo de documento
     * @param issuerId ID del emisor
     * @param isActive Estado activo
     * @return Secuencia encontrada
     */
    Optional<InvoiceIssuingSequence> findByDocumentTypeAndInvoiceIssuer_IdAndIsActive(
            String documentType, UUID issuerId, Boolean isActive);
}
