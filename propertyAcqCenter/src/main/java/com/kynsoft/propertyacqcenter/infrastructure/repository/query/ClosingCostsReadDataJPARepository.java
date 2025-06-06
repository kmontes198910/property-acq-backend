package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.ClosingCosts;
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
public interface ClosingCostsReadDataJPARepository extends JpaRepository<ClosingCosts, UUID>, JpaSpecificationExecutor<ClosingCosts> {
    @EntityGraph(attributePaths = {"property"})
    @Override
    Page<ClosingCosts> findAll(Specification<ClosingCosts> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"property"})
    @Override
    Optional<ClosingCosts> findById(UUID id);

    @EntityGraph(attributePaths = {"property"})
    Optional<ClosingCosts> findByPropertyId(String propertyId);
}