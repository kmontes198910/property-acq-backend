package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyAddress;
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
public interface CompanyAddressReadDataJPARepository extends JpaRepository<CompanyAddress, UUID>, JpaSpecificationExecutor<CompanyAddress> {
    @Override
    Page<CompanyAddress> findAll(Specification<CompanyAddress> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"company", "company.companyType"})
    @Override
    Optional<CompanyAddress> findById(UUID id);
}