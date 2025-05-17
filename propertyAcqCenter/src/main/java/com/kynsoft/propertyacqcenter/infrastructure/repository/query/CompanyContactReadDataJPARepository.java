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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CompanyContactReadDataJPARepository extends JpaRepository<CompanyContact, UUID>, JpaSpecificationExecutor<CompanyContact> {
    @EntityGraph(attributePaths = {"company"})
    @Override
    Page<CompanyContact> findAll(Specification<CompanyContact> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"company", "company.companyType", "company.business", "company.subCompanyType"})
    @Override
    Optional<CompanyContact> findById(UUID id);

    @Query("SELECT COUNT(cc) FROM CompanyContact cc WHERE cc.email = :email AND cc.id <> :id")
    long countByEmail(@Param("email") String email, @Param("id") UUID id);

    @Query("SELECT COUNT(cc) FROM CompanyContact cc WHERE cc.personalEmail = :personalEmail AND cc.id <> :id")
    long countByPersonalEmail(@Param("personalEmail") String personalEmail, @Param("id") UUID id);
}