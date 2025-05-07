package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.PropertyImages;
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
public interface PropertyImagesReadDataJPARepository extends JpaRepository<PropertyImages, UUID>, JpaSpecificationExecutor<PropertyImages> {
    @Override
    Page<PropertyImages> findAll(Specification<PropertyImages> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"property"})
    @Override
    Optional<PropertyImages> findById(UUID id);
}