package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.BankAccount;
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
public interface BankAccountReadDataJPARepository extends JpaRepository<BankAccount, UUID>, JpaSpecificationExecutor<BankAccount> {
    Page<BankAccount> findAll(Specification<BankAccount> specification, Pageable pageable);
    
    List<BankAccount> findByLegalEntityId(UUID legalEntityId);
    
    @Query("SELECT b FROM BankAccount b WHERE b.legalEntity.id = :legalEntityId AND b.isPrimary = true")
    Optional<BankAccount> findPrimaryBankAccountByLegalEntityId(@Param("legalEntityId") UUID legalEntityId);
}
