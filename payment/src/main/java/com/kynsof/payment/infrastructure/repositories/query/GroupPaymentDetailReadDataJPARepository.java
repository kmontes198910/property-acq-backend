package com.kynsof.payment.infrastructure.repositories.query;

import com.kynsof.payment.infrastructure.entity.Billing;
import com.kynsof.payment.infrastructure.entity.GroupPayment;
import com.kynsof.payment.infrastructure.entity.PaymentDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface GroupPaymentDetailReadDataJPARepository extends JpaRepository<PaymentDetail, UUID>, JpaSpecificationExecutor<PaymentDetail> {
    Page<PaymentDetail> findAll(Specification specification, Pageable pageable);
    List<PaymentDetail> findByGroupPayment(GroupPayment groupPayment);


    @Query("SELECT pd.billing.code AS serviceCode, COUNT(pd.id) AS serviceCount, " +
            "SUM(pd.amount) AS totalAmount, " +
            "SUM(CASE WHEN pd.groupPayment.paymentType = 'PLACETOPAY' THEN pd.amount ELSE 0 END) AS placetopayAmount, " +
            "SUM(CASE WHEN pd.groupPayment.paymentType = 'CASH' THEN pd.amount ELSE 0 END) AS cashAmount, " +
            "SUM(CASE WHEN pd.groupPayment.paymentType = 'TRANSFER' THEN pd.amount ELSE 0 END) AS transferAmount " +
            "FROM PaymentDetail pd " +
            "WHERE pd.groupPayment.paymentDate BETWEEN :startDate AND :endDate " +
            "AND pd.billing.business.id = :businessId " +
            "AND pd.groupPayment.status = 'PAYMENT_APPROVED' " +
            "GROUP BY pd.billing.code")
    List<Object[]> findGroupedPaymentsByServiceAndBusiness(@Param("startDate") LocalDateTime startDate,
                                                           @Param("endDate") LocalDateTime endDate,
                                                           @Param("businessId") UUID businessId);

    @Query("SELECT pd.billing FROM PaymentDetail pd WHERE pd.billing.code = :serviceCode ORDER BY pd.billing.createdAt ASC")
    List<Billing> findBillingByServiceCode(@Param("serviceCode") String serviceCode);

    @Query("SELECT COUNT(DISTINCT pd.groupPayment.id) FROM PaymentDetail pd " +
            "WHERE pd.groupPayment.paymentDate BETWEEN :startDate AND :endDate " +
            "AND pd.billing.business.id = :businessId " +
            "AND pd.groupPayment.status = 'PAYMENT_APPROVED'")
    Long countDistinctGroupPayments(@Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate,
                                    @Param("businessId") UUID businessId);
}
