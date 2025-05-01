package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
import com.kynsoft.propertyacqcenter.domain.enums.ContactRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface CompanyReadDataJPARepository extends JpaRepository<Company, UUID>, JpaSpecificationExecutor<Company> {
    @Override
    Page<Company> findAll(Specification<Company> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"legalEntity", "legalEntity.business", "companyType"})
    @Override
    Optional<Company> findById(UUID id);

    List<Company> findByLegalEntityId(UUID legalEntityId);
    
    @Query("SELECT c FROM Company c WHERE c.legalEntity.id = :legalEntityId AND c.isPrimary = true")
    Optional<Company> findPrimaryContactByLegalEntityId(@Param("legalEntityId") UUID legalEntityId);
    
    @Query("SELECT c FROM Company c WHERE c.legalEntity.id = :legalEntityId AND c.role = :role")
    List<Company> findByLegalEntityIdAndRole(
            @Param("legalEntityId") UUID legalEntityId, 
            @Param("role") ContactRole role);
}