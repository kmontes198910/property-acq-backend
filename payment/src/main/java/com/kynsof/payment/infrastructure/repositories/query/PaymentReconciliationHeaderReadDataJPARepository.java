package com.kynsof.payment.infrastructure.repositories.query;

import com.kynsof.payment.infrastructure.entity.PaymentReconciliationHeader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PaymentReconciliationHeaderReadDataJPARepository extends JpaRepository<PaymentReconciliationHeader, UUID>, JpaSpecificationExecutor<PaymentReconciliationHeader> {
    Page<PaymentReconciliationHeader> findAll(Specification specification, Pageable pageable);

}
