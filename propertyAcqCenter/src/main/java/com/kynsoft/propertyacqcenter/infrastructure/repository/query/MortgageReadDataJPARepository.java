package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Mortgage;
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
public interface MortgageReadDataJPARepository extends JpaRepository<Mortgage, UUID>, JpaSpecificationExecutor<Mortgage> {
    @EntityGraph(attributePaths = {"property"})
    @Override
    Page<Mortgage> findAll(Specification<Mortgage> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"property"})
    @Override
    Optional<Mortgage> findById(UUID id);
}