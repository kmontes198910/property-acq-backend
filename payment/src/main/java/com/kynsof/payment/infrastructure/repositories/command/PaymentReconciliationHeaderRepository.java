package com.kynsof.payment.infrastructure.repositories.command;

import com.kynsof.payment.infrastructure.entity.PaymentReconciliationHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PaymentReconciliationHeaderRepository extends JpaRepository<PaymentReconciliationHeader, UUID> {
}