package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Document;
import com.kynsoft.propertyacqcenter.infrastructure.entity.enums.DocumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentReadDataJPARepository extends JpaRepository<Document, UUID>, JpaSpecificationExecutor<Document> {
    Page<Document> findAll(Specification<Document> specification, Pageable pageable);
    
    List<Document> findByLegalEntityId(UUID legalEntityId);
    
    @Query("SELECT d FROM Document d WHERE d.legalEntity.id = :legalEntityId AND d.documentType = :documentType")
    List<Document> findByLegalEntityIdAndDocumentType(
            @Param("legalEntityId") UUID legalEntityId, 
            @Param("documentType") DocumentType documentType);
}