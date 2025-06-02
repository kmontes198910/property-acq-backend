package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.OwnerShipLegalEntity;
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
public interface OwnerShipLegalEntityReadDataJPARepository extends JpaRepository<OwnerShipLegalEntity, UUID>, JpaSpecificationExecutor<OwnerShipLegalEntity> {
    @EntityGraph(attributePaths = {"legalEntity", "ownerDocuments"})
    @Override
    Page<OwnerShipLegalEntity> findAll(Specification<OwnerShipLegalEntity> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"legalEntity", "ownerDocuments"})
    @Override
    Optional<OwnerShipLegalEntity> findById(UUID id);
}