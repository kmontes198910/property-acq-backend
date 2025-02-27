package com.kynsof.payment.infrastructure.repositories.query;

import com.kynsof.payment.domain.dto.enumDto.BillingStatus;
import com.kynsof.payment.infrastructure.entity.Billing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BillingReadDataJPARepository extends JpaRepository<Billing, UUID>, JpaSpecificationExecutor<Billing> {
    Page<Billing> findAll(Specification specification, Pageable pageable);
    boolean existsByCodeAndBusinessIdAndStatusAndClientId(String code, UUID businessId, BillingStatus status, UUID clientId);

}
