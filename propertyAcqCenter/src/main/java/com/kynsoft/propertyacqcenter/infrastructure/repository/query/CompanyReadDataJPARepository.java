package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
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
public interface CompanyReadDataJPARepository extends JpaRepository<Company, UUID>, JpaSpecificationExecutor<Company> {
    @EntityGraph(attributePaths = { "companyType", "business", "subCompanyType", "subCategory", "legalInformation", "seller"})
    @Override
    Page<Company> findAll(Specification<Company> specification, Pageable pageable);

    @EntityGraph(attributePaths = { "companyType", "business", "subCompanyType", "subCategory", "legalInformation", "seller"})
    @Override
    Optional<Company> findById(UUID id);
}
