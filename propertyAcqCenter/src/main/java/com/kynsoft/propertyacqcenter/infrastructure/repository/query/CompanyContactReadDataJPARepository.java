package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyContact;
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
public interface CompanyContactReadDataJPARepository extends JpaRepository<CompanyContact, UUID>, JpaSpecificationExecutor<CompanyContact> {
    @EntityGraph(attributePaths = {"company"})
    @Override
    Page<CompanyContact> findAll(Specification<CompanyContact> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"company", "company.companyType", "company.business", "company.subCompanyType"})
    @Override
    Optional<CompanyContact> findById(UUID id);
}