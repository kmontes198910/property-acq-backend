package com.kynsoft.finamer.propertyacqcenter.infrastructure.repository.command;

import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactWriteDataJPARepository extends JpaRepository<Contact, UUID> {
    // Las operaciones básicas de escritura están heredadas de JpaRepository:
    // save(), deleteById(), deleteAll(), etc.
}