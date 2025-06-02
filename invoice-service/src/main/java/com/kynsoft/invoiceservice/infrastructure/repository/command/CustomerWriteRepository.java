package com.kynsoft.invoiceservice.infrastructure.repository.command;

import com.kynsoft.invoiceservice.infrastructure.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerWriteRepository extends JpaRepository<Customer, UUID> {
}