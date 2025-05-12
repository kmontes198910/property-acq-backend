package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.BankDocument;
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
public interface BankDocumentReadDataJPARepository extends JpaRepository<BankDocument, UUID>, JpaSpecificationExecutor<BankDocument> {
    @EntityGraph(attributePaths = {"bankAccount"})
    @Override
    Page<BankDocument> findAll(Specification<BankDocument> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"bankAccount", "bankAccount.legalEntity"})
    @Override
    Optional<BankDocument> findById(UUID id);
}