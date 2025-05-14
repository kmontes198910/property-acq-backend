package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.OwnerDocument;
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
public interface OwnerDocumentReadDataJPARepository extends JpaRepository<OwnerDocument, UUID>, JpaSpecificationExecutor<OwnerDocument> {
    @EntityGraph(attributePaths = {"owner", "owner.legalEntity"})
    @Override
    Page<OwnerDocument> findAll(Specification<OwnerDocument> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"owner", "owner.legalEntity"})
    @Override
    Optional<OwnerDocument> findById(UUID id);
}