package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceIssuerRepository extends JpaRepository<InvoiceIssuer, UUID> {
    Optional<InvoiceIssuer> findByRuc(String ruc);
    
    // Por lo general en una implementación real tendríamos un método para obtener el emisor activo/predeterminado
    Optional<InvoiceIssuer> findFirstByActiveTrue();
}