package com.kynsof.treatments.infrastructure.repositories.query;

import com.kynsof.treatments.domain.dto.enumDto.BillingStatus;
import com.kynsof.treatments.infrastructure.entity.Billing;
import com.kynsof.treatments.infrastructure.entity.GroupPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface GroupPaymentReadDataJPARepository extends JpaRepository<GroupPayment, UUID>, JpaSpecificationExecutor<GroupPayment> {
    Page<GroupPayment> findAll(Specification specification, Pageable pageable);

}
