package com.kynsof.calendar.domain.service;

import com.kynsof.calendar.domain.dto.AvailableDateDto;
import com.kynsof.calendar.domain.dto.ReservationRequestDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.infrastructure.entity.Schedule;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface IScheduleService {
    void delete(UUID id);
    ScheduleDto findById(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
    PaginatedResponse getResourcesWithContinuousAvailability(ReservationRequestDto reservationRequest, Pageable pageable);
    void delete(Schedule schedule);
    UUID create(ScheduleDto schedule);
    void createAll(List<ScheduleDto> schedule);
    void update(ScheduleDto schedule);
    List<LocalDate> findDistinctAvailableDatesByServiceIdAndDateRange(UUID serviceId,LocalDate startDate, LocalDate endDate);
    List<AvailableDateDto> getAvailableDatesAndSlots(UUID resourceId, UUID businessId,  LocalDate startDate, LocalDate endDate);
    List<Schedule> findOverlappingSchedules(UUID resourceId, LocalDate date, LocalTime startTime, LocalTime endingTime);
    boolean existsOverlappingSchedule(UUID resourceId, LocalDate date, LocalTime startTime, LocalTime endingTime);

    List<ScheduleDto> findSchedulesWithEqualStock(LocalDate date);
    PaginatedResponse getUniqueAvailableServices(Pageable pageable,  List<FilterCriteria> filterCriteria);
}
