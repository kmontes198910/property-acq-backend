package com.kynsof.payment.infrastructure.repositories.command;



import com.kynsof.payment.infrastructure.entity.PaymentReconciliationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PaymentReconciliationDetailRepository extends JpaRepository<PaymentReconciliationDetail, UUID> {
}