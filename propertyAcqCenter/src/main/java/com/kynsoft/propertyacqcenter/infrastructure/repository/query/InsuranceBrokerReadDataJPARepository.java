package com.kynsoft.propertyacqcenter.infrastructure.repository.query;

import com.kynsoft.propertyacqcenter.infrastructure.entity.InsuranceBroker;
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
public interface InsuranceBrokerReadDataJPARepository extends JpaRepository<InsuranceBroker, UUID>, JpaSpecificationExecutor<InsuranceBroker> {
    @EntityGraph(attributePaths = {"property", "buyer"})
    @Override
    Page<InsuranceBroker> findAll(Specification<InsuranceBroker> specification, Pageable pageable);

    @EntityGraph(attributePaths = {"property", "buyer"})
    @Override
    Optional<InsuranceBroker> findById(UUID id);
}