package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceIssuerRepository extends JpaRepository<Issuer, UUID>, JpaSpecificationExecutor<Issuer> {
    Optional<Issuer> findByRuc(String ruc);
    
    // Por lo general en una implementación real tendríamos un método para obtener el emisor activo/predeterminado
    Optional<Issuer> findFirstByStatusTrue();
}