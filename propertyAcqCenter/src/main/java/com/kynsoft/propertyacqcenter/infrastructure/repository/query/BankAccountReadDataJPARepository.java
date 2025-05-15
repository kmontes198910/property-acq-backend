package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.BankAccount;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;

@Repository
public interface BankAccountReadDataJPARepository extends JpaRepository<BankAccount, UUID>, JpaSpecificationExecutor<BankAccount> {
    @EntityGraph(attributePaths = {"legalEntity", "currency", "bankDocuments"})
    @Override
    Page<BankAccount> findAll(Specification<BankAccount> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"legalEntity.business", "legalEntity", "legalEntity.parent", "legalEntity.owners", "currency"})
    @Override
    Optional<BankAccount> findById(UUID id);
}
