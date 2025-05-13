package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCategoryRealEstateCompanyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubCategoryRealEstateCompanyTypeReadDataJPARepository extends JpaRepository<SubCategoryRealEstateCompanyType, UUID>, JpaSpecificationExecutor<SubCategoryRealEstateCompanyType> {
    @Override
    Page<SubCategoryRealEstateCompanyType> findAll(Specification<SubCategoryRealEstateCompanyType> specification, Pageable pageable);
}