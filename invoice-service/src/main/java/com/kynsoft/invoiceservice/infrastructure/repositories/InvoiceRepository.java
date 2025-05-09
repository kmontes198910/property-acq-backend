package com.kynsoft.invoiceservice.infrastructure.repositories;

import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}