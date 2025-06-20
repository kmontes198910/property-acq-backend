package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
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
public interface AdquisitionPropertyReadDataJPARepository extends JpaRepository<AdquisitionProperty, UUID>, JpaSpecificationExecutor<AdquisitionProperty> {
    @EntityGraph(attributePaths = {"buyer", "property", "contact", "contact.company"})
    @Override
    Page<AdquisitionProperty> findAll(Specification<AdquisitionProperty> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"buyer", "property", "contact", "contact.company"})
    @Override
    Optional<AdquisitionProperty> findById(UUID id);
}