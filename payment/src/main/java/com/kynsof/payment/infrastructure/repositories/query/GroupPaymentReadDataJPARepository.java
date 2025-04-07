package com.kynsof.payment.infrastructure.repositories.query;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.infrastructure.entity.GroupPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupPaymentReadDataJPARepository extends JpaRepository<GroupPayment, UUID>, JpaSpecificationExecutor<GroupPayment> {
    Page<GroupPayment> findAll(Specification specification, Pageable pageable);

    @Query("SELECT g FROM GroupPayment g WHERE g.status = :status")
    List<GroupPayment> findByStatus(@Param("status") GroupPaymentStatus status);

    @Query("SELECT g FROM GroupPayment g WHERE g.requestId = :requestId")
    Optional<GroupPayment> findByRequestId(String requestId);
}
