package com.kynsoft.finamer.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.ContactPerson;
import com.kynsoft.finamer.propertyacqcenter.infrastructure.entity.enums.ContactRole;
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

@Repository
public interface ContactPersonReadDataJPARepository extends JpaRepository<ContactPerson, UUID>, JpaSpecificationExecutor<ContactPerson> {
    Page<ContactPerson> findAll(Specification<ContactPerson> specification, Pageable pageable);
    
    List<ContactPerson> findByLegalEntityId(UUID legalEntityId);
    
    @Query("SELECT c FROM ContactPerson c WHERE c.legalEntity.id = :legalEntityId AND c.isPrimary = true")
    Optional<ContactPerson> findPrimaryContactByLegalEntityId(@Param("legalEntityId") UUID legalEntityId);
    
    @Query("SELECT c FROM ContactPerson c WHERE c.legalEntity.id = :legalEntityId AND c.role = :role")
    List<ContactPerson> findByLegalEntityIdAndRole(
            @Param("legalEntityId") UUID legalEntityId, 
            @Param("role") ContactRole role);
}