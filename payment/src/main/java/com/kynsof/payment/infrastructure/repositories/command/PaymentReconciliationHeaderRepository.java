package com.kynsof.payment.infrastructure.repositories.command;

import com.kynsof.payment.infrastructure.entity.PaymentReconciliationHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PaymentReconciliationHeaderRepository extends JpaRepository<PaymentReconciliationHeader, UUID> {
    @Query("SELECT COUNT(ph) FROM PaymentReconciliationHeader ph WHERE ph.startDate = :startDate AND ph.business.id = :businessId")
    Long countByDateAndBusiness(@Param("startDate") LocalDateTime startDate, @Param("businessId") UUID businessId);
}