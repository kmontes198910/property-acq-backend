package com.kynsoft.propertyacqcenter.infrastructure.repository.command;

import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyTypeWriteDataJPARepository extends JpaRepository<CompanyType, UUID> {
    // Las operaciones básicas de escritura están heredadas de JpaRepository:
    // save(), deleteById(), deleteAll(), etc.
}