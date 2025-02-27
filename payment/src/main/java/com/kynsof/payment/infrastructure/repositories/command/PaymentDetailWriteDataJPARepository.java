package com.kynsof.payment.infrastructure.repositories.command;

import com.kynsof.payment.infrastructure.entity.Billing;
import com.kynsof.payment.infrastructure.entity.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentDetailWriteDataJPARepository extends JpaRepository<PaymentDetail, UUID> {

}
