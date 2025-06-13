package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.SalesProperty;
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
public interface SalesPropertyReadDataJPARepository extends JpaRepository<SalesProperty, UUID>, JpaSpecificationExecutor<SalesProperty> {
    @EntityGraph(attributePaths = {"property"})
    @Override
    Page<SalesProperty> findAll(Specification<SalesProperty> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"property"})
    @Override
    Optional<SalesProperty> findById(UUID id);

    @EntityGraph(attributePaths = {"property"})
    Optional<SalesProperty> findByPropertyId(String propertyId);

    boolean existsByPropertyId(String propertyId);
}