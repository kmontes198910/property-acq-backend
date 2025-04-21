package com.kynsoft.finamer.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.LegalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LegalEntityReadDataJPARepository extends JpaRepository<LegalEntity, UUID>, JpaSpecificationExecutor<LegalEntity> {
    Page<LegalEntity> findAll(Specification<LegalEntity> specification, Pageable pageable);
    
    Optional<LegalEntity> findByTaxId(String taxId);
}