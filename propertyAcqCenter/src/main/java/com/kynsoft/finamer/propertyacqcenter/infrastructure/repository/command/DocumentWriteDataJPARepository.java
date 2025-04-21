package com.kynsoft.finamer.propertyacqcenter.infrastructure.repository.command;

import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentWriteDataJPARepository extends JpaRepository<Document, UUID> {
}