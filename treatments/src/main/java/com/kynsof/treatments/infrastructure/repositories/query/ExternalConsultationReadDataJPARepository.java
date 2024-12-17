package com.kynsof.treatments.infrastructure.repositories.query;

import com.kynsof.treatments.infrastructure.entity.ExternalConsultation;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ExternalConsultationReadDataJPARepository extends JpaRepository<ExternalConsultation, UUID>, JpaSpecificationExecutor<ExternalConsultation> {

    Page<ExternalConsultation> findAll(Specification specification, Pageable pageable);

    @Query("SELECT COUNT(ec) FROM ExternalConsultation ec JOIN ec.business b WHERE b.id = :businessId AND  ec.consultationTime >= :startDate AND ec.consultationTime < :endDate")
    Long countConsultationsByBusinessAndDateRange(@Param("businessId") UUID businessId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT MONTH(ec.consultationTime), COUNT(ec) " +
            "FROM ExternalConsultation ec " +
            "WHERE YEAR(ec.consultationTime) = :year " +
            "AND ec.business.id = :businessId " +
            "GROUP BY MONTH(ec.consultationTime) " +
            "ORDER BY MONTH(ec.consultationTime)")
    List<Object[]> countConsultationsByMonth(@Param("businessId") UUID businessId, @Param("year") int year);

    @Query("SELECT ec.service.name, COUNT(ec) " +
            "FROM ExternalConsultation ec " +
            "WHERE ec.business.id = :businessId " +
            "AND EXTRACT(YEAR FROM ec.consultationTime) = :year " +
            "GROUP BY ec.service.name " +
            "ORDER BY COUNT(ec) DESC")
    List<Object[]> findTop10SpecialitiesByServiceAndBusinessIdAndYear(
            @Param("businessId") UUID businessId,
            @Param("year") int year,
            Pageable pageable);

}
