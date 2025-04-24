package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.ConstructionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Repository
public interface ConstructionTypeReadDataJPARepository extends JpaRepository<ConstructionType, UUID>, JpaSpecificationExecutor<ConstructionType> {
    Page<ConstructionType> findAll(Specification<ConstructionType> specification, Pageable pageable);
}