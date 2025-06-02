package com.kynsoft.propertyacqcenter.infrastructure.repository.command;

import com.kynsoft.propertyacqcenter.infrastructure.entity.BankDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankDocumentWriteDataJPARepository extends JpaRepository<BankDocument, UUID> {
}