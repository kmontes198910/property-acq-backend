package com.kynsof.payment.infrastructure.repositories.query;

import com.kynsof.payment.infrastructure.entity.GroupPayment;
import com.kynsof.payment.infrastructure.entity.PaymentDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface GroupPaymentDetailReadDataJPARepository extends JpaRepository<PaymentDetail, UUID>, JpaSpecificationExecutor<PaymentDetail> {
    Page<PaymentDetail> findAll(Specification specification, Pageable pageable);
    List<PaymentDetail> findByGroupPayment(GroupPayment groupPayment);
}
