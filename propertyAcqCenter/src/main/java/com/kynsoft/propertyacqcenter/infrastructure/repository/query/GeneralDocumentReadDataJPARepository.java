package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.GeneralDocument;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface GeneralDocumentReadDataJPARepository extends JpaRepository<GeneralDocument, UUID>, JpaSpecificationExecutor<GeneralDocument> {
    @EntityGraph(attributePaths = {"documentType", "adquisitionProperty"})
    @Override
    Page<GeneralDocument> findAll(Specification<GeneralDocument> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"documentType", "adquisitionProperty"})
    @Override
    Optional<GeneralDocument> findById(UUID id);
}