package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Income;
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
public interface IncomeReadDataJPARepository extends JpaRepository<Income, UUID>, JpaSpecificationExecutor<Income> {
    @EntityGraph(attributePaths = {"property"})
    @Override
    Page<Income> findAll(Specification<Income> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"property"})
    @Override
    Optional<Income> findById(UUID id);

    @EntityGraph(attributePaths = {"property"})
    Optional<Income> findByPropertyId(String propertyId);
}