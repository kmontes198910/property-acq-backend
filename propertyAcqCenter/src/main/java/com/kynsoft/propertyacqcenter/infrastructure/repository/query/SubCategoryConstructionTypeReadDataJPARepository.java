package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCategoryConstructionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubCategoryConstructionTypeReadDataJPARepository extends JpaRepository<SubCategoryConstructionType, UUID>, JpaSpecificationExecutor<SubCategoryConstructionType> {
    @Override
    Page<SubCategoryConstructionType> findAll(Specification<SubCategoryConstructionType> specification, Pageable pageable);
}