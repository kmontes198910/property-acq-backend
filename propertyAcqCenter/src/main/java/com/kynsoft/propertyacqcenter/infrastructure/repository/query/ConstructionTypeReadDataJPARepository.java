package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Repository
public interface ConstructionTypeReadDataJPARepository extends JpaRepository<SubCompanyType, UUID>, JpaSpecificationExecutor<SubCompanyType> {
    Page<SubCompanyType> findAll(Specification<SubCompanyType> specification, Pageable pageable);
}