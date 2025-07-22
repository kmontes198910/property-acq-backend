package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.domain.enums.DocumentType;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface DocumentReadDataJPARepository extends JpaRepository<Document, UUID>, JpaSpecificationExecutor<Document> {
    @EntityGraph(attributePaths = {"legalEntity"})
    @Override
    Page<Document> findAll(Specification<Document> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"legalEntity", "legalEntity.parent"})
    @Override
    Optional<Document> findById(UUID id);

    @Query("SELECT COUNT(a) FROM Document a WHERE a.legalEntity.id = :legalEntity AND a.documentType = :documentType AND a.id <> :id")
    int countByDocumentTypeAndLegalEntity(@Param("legalEntity") UUID legalEntity, @Param("documentType") DocumentType documentType, @Param("id") UUID id);

    @Query("SELECT COUNT(a) FROM Document a WHERE a.legalEntity.id = :legalEntity AND a.documentType = :documentType")
    int countByDocumentTypeAndLegalEntity(@Param("legalEntity") UUID legalEntity, @Param("documentType") DocumentType documentType);
}