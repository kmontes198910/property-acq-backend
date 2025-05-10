package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

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

@Repository
public interface DocumentReadDataJPARepository extends JpaRepository<Document, UUID>, JpaSpecificationExecutor<Document> {
    @EntityGraph(attributePaths = {"legalEntity"})
    @Override
    Page<Document> findAll(Specification<Document> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"legalEntity", "legalEntity.parent"})
    @Override
    Optional<Document> findById(UUID id);
}