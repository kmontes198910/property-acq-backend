package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Insurance;
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
public interface InsuranceReadDataJPARepository extends JpaRepository<Insurance, UUID>, JpaSpecificationExecutor<Insurance> {
    @EntityGraph(attributePaths = {"legalEntity"})
    @Override
    Page<Insurance> findAll(Specification<Insurance> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"legalEntity", "legalEntity.business"})
    @Override
    Optional<Insurance> findById(UUID id);
}