package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Expenses;
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
public interface ExpensesReadDataJPARepository extends JpaRepository<Expenses, UUID>, JpaSpecificationExecutor<Expenses> {
    @EntityGraph(attributePaths = {"property"})
    @Override
    Page<Expenses> findAll(Specification<Expenses> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"property"})
    @Override
    Optional<Expenses> findById(UUID id);

    @EntityGraph(attributePaths = {"property"})
    Optional<Expenses> findByPropertyId(String propertyId);
}