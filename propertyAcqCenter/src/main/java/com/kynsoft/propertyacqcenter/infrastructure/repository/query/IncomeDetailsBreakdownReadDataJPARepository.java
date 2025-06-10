package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.IncomeDetailsBreakdown;
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
public interface IncomeDetailsBreakdownReadDataJPARepository extends JpaRepository<IncomeDetailsBreakdown, UUID>, JpaSpecificationExecutor<IncomeDetailsBreakdown> {
    @EntityGraph(attributePaths = {"detailsBreakdown"})
    @Override
    Page<IncomeDetailsBreakdown> findAll(Specification<IncomeDetailsBreakdown> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"detailsBreakdown"})
    @Override
    Optional<IncomeDetailsBreakdown> findById(UUID id);
}