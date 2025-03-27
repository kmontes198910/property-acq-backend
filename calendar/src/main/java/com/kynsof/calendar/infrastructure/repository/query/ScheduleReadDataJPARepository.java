package com.kynsof.calendar.infrastructure.repository.query;

import com.kynsof.calendar.domain.dto.ScheduleAvailabilityDto;
import com.kynsof.calendar.domain.dto.ScheduleServiceInfoDto;
import com.kynsof.calendar.infrastructure.entity.Business;
import com.kynsof.calendar.infrastructure.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface ScheduleReadDataJPARepository extends JpaRepository<Schedule, UUID>, JpaSpecificationExecutor<Schedule> {
    Page<Schedule> findAll(Pageable pageable);
    Page<Schedule> findAll(Specification specification, Pageable pageable);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE s.resource.id = :resourceId " +
       "AND s.date = :date " +
       "AND ((s.startTime < :endTime AND s.endingTime > :startTime))")
    boolean existsOverlappingSchedule(@Param("resourceId") UUID resourceId,
                                    @Param("date") LocalDate date,
                                    @Param("startTime") LocalTime startTime,
                                    @Param("endTime") LocalTime endingTime);

    @Query("SELECT s FROM Schedule s WHERE s.resource.id = :resourceId " +
                  "AND s.date = :date " +
                  "AND ((s.startTime < :endTime AND s.endingTime > :startTime))")
    List<Schedule> findOverlappingSchedules(@Param("resourceId") UUID resourceId,
                                            @Param("date") LocalDate date,
                                            @Param("startTime") LocalTime startTime,
                                            @Param("endTime") LocalTime endingTime);



    @Query("SELECT DISTINCT s.business FROM Schedule s " +
            "WHERE s.date = :date " +
            "AND s.service.id = :serviceId " +
            "AND s.stock > 0")
    Page<Business> findBusinessesWithAvailableStockByDateAndService(@Param("date") LocalDate date,
                                                                    @Param("serviceId") UUID serviceId,
                                                                    Pageable pageable);


    @Query("SELECT s FROM Schedule s JOIN s.service ser JOIN s.business bus " +
            "WHERE ser.id = :serviceId AND bus.id = :businessId AND s.date = :date " +
            "AND s.stock > 0")
    Page<Schedule> findSchedulesWithStockByBusinessServiceAndDate(UUID businessId, UUID serviceId, LocalDate date, Pageable pageable);


    @Query("SELECT DISTINCT s.date FROM Schedule s WHERE s.status = 'AVAILABLE' AND s.service.id = :serviceId AND s.date BETWEEN :startDate AND :endDate ORDER BY s.date ASC")
    List<LocalDate> findDistinctAvailableDatesByServiceIdAndDateRange(UUID serviceId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT new com.kynsof.calendar.domain.dto.ScheduleAvailabilityDto(s.date, s.startTime, s.endingTime, s.id) " +
            "FROM Schedule s " +
            "WHERE s.resource.id = :resourceId " +
            "AND s.business.id = :businessId " +
            "AND s.date BETWEEN :startDate AND :endDate " +
            "AND s.status = 'AVAILABLE' " +
            "AND s.stock > 0 " +
            "ORDER BY s.date ASC, s.startTime ASC")
    List<ScheduleAvailabilityDto> findAvailableSchedulesByResourceAndBusinessAndDateRange(
            @Param("resourceId") UUID resourceId,
            @Param("businessId") UUID businessId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);



    @Query("SELECT new com.kynsof.calendar.domain.dto.ScheduleServiceInfoDto(" +
            "s.business.id, s.business.name, s.business.address, s.business.logo, " +
            "MIN(bs.price), s.business.latitude, s.business.longitude,s.business.phone,s.business.email, s.business.ruc) " +
            "FROM Schedule s " +
            "JOIN s.business b " +
            "JOIN BusinessServices bs ON bs.business = b AND bs.services.id = s.service.id " +
            "WHERE s.service.id = :serviceId " +
            "AND s.date BETWEEN :startDate AND :endDate " +
            "AND s.status = 'AVAILABLE' " +
            "AND b.name LIKE CONCAT('%', :businessName, '%') " +
            "AND s.stock > 0 " +
            "GROUP BY s.business.id, s.business.name, s.business.address, s.business.logo, s.business.latitude, s.business.longitude " +
            "ORDER BY MIN(bs.price) ASC")
    Page<ScheduleServiceInfoDto> findDetailedAvailableSchedulesByResourceAndBusinessAndDateRange(
            @Param("serviceId") UUID serviceId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("businessName") String businessName,
            Pageable pageable);

    @Query("SELECT s FROM Schedule s WHERE s.initialStock = s.stock AND s.date <= :date and  s.status='AVAILABLE'")
    List<Schedule> findSchedulesWithEqualStock(@Param("date") LocalDate date);

    @Query("SELECT r, s " +
            "FROM Schedule s JOIN s.resource r " +
            "WHERE s.service.id = :serviceId " +
            "AND (:businessId IS NULL OR s.business.id = :businessId) " +
            "AND s.date IN :dates " +
            "AND s.status = 'AVAILABLE' " +
      "AND s.startTime <= :startTime " + // Comentado
      "AND s.endingTime >= :endingTime " + // Comentado
            "GROUP BY r.id, r.name, r.status, r.image, r.createdAt, r.updatedAt, s.id, s.date, s.startTime, s.endingTime, s.initialStock, s.stock, s.createdAt, s.updatedAt, s.status " +
            "HAVING COUNT(s.date) = :totalDays")
    List<Object[]> findResourcesAndSchedulesForServiceWithFullAvailability(
            @Param("serviceId") UUID serviceId,
            @Param("businessId") UUID businessId,
            @Param("dates") List<LocalDate> dates,
          @Param("startTime") LocalTime startTime, // Comentado
          @Param("endingTime") LocalTime endingTime, // Comentado
            @Param("totalDays") long totalDays,
            Pageable pageable);
}
