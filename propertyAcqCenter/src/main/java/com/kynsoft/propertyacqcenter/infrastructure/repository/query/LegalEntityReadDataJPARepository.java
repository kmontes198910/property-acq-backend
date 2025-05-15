package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
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
public interface LegalEntityReadDataJPARepository extends JpaRepository<LegalEntity, UUID>, JpaSpecificationExecutor<LegalEntity> {
    @EntityGraph(attributePaths = {"business", "parent", "owners"})
    @Override
    Page<LegalEntity> findAll(Specification<LegalEntity> specification, Pageable pageable);

    Optional<LegalEntity> findByTaxId(String taxId);

    @EntityGraph(attributePaths = {"business", "parent", "owners"})
    @Override
    Optional<LegalEntity> findById(UUID id);

    long countByTaxId(String taxId);
}