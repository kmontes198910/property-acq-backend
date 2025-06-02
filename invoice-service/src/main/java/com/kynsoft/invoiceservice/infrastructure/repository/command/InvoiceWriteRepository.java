package com.kynsoft.invoiceservice.infrastructure.repository.command;

import com.kynsoft.invoiceservice.infrastructure.entities.Customer;
import com.kynsoft.invoiceservice.infrastructure.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceWriteRepository extends JpaRepository<Invoice, UUID> {
}
