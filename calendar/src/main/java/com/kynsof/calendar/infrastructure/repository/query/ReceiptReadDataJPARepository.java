package com.kynsof.calendar.infrastructure.repository.query;

import com.kynsof.calendar.infrastructure.entity.Receipt;
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

public interface ReceiptReadDataJPARepository extends JpaRepository<Receipt, UUID>, JpaSpecificationExecutor<Receipt> {

    Page<Receipt> findAll(Specification specification, Pageable pageable);

    Optional<Receipt> findByRequestId(String requestId);

    @Query("SELECT r.status, COUNT(r) " +
            "FROM Receipt r " +
            "WHERE r.schedule.business.id = :businessId " +
            "GROUP BY r.status " +
            "ORDER BY r.status")
    List<Object[]> countAppointmentsByStatusForBusiness(@Param("businessId") UUID businessId);
}
