package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCompanyType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface SubCompanyTypeReadDataJPARepository extends JpaRepository<SubCompanyType, UUID>, JpaSpecificationExecutor<SubCompanyType> {
    @Override
    Page<SubCompanyType> findAll(Specification<SubCompanyType> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"companyType"})
    @Override
    Optional<SubCompanyType> findById(UUID id);
}