package com.kynsof.calendar.application.service;

import com.kynsof.calendar.application.query.ScheduleResponse;
import com.kynsof.calendar.application.query.schedule.getAvailabilityByRangeDate.ResourceWithSchedulesResponse;
import com.kynsof.calendar.application.query.schedule.getAvailabilityByRangeDate.ScheduleSimpleResponse;
import com.kynsof.calendar.domain.dto.*;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.calendar.domain.service.IScheduleService;
import com.kynsof.calendar.infrastructure.config.CalendarCacheConfig;
import com.kynsof.calendar.infrastructure.entity.Resource;
import com.kynsof.calendar.infrastructure.entity.Schedule;
import com.kynsof.calendar.infrastructure.entity.Services;
import com.kynsof.calendar.infrastructure.repository.command.ScheduleWriteDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.query.ScheduleReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.share.core.infrastructure.specifications.LogicalOperation;
import com.kynsof.share.core.infrastructure.specifications.SearchOperation;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements IScheduleService {

    private final ScheduleReadDataJPARepository repositoryQuery;
    private final ScheduleWriteDataJPARepository repositoryCommand;

    public ScheduleServiceImpl(ScheduleReadDataJPARepository repositoryQuery, ScheduleWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, 
               key = "'availableDates:' + #serviceId + ':' + #startDate + ':' + #endDate", 
               unless = "#result == null || #result.isEmpty()")
    public List<LocalDate> findDistinctAvailableDatesByServiceIdAndDateRange(UUID serviceId, LocalDate startDate, LocalDate endDate) {
        return repositoryQuery.findDistinctAvailableDatesByServiceIdAndDateRange(serviceId, startDate, endDate);
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, 
               key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #filterCriteria?.hashCode()", 
               unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        filterCriteria.forEach(filter -> {
            if ("status".equals(filter.getKey()) && filter.getValue() instanceof String) {
                convertEnumFilter(filter, EStatusSchedule.class);
            }
        });

        GenericSpecificationsBuilder<Schedule> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Schedule> schedules = repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(schedules);
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, 
               key = "'resourcesAvailability:' + #reservationRequest.serviceId + ':' + #reservationRequest.businessId + ':' + #reservationRequest.startDate + ':' + #reservationRequest.endDate", 
               unless = "#result == null")
    public PaginatedResponse getResourcesWithContinuousAvailability(ReservationRequestDto reservationRequest, Pageable pageable) {
        List<LocalDate> requestedDates = generateDateRange(reservationRequest.getStartDate(), reservationRequest.getEndDate());

        List<Object[]> results = repositoryQuery.findResourcesAndSchedulesForServiceWithFullAvailability(
                reservationRequest.getServiceId(),
                reservationRequest.getBusinessId(),
                requestedDates,
                LocalTime.of(8, 0),
                LocalTime.of(17, 0),
                requestedDates.size(),
                pageable
        );

        Map<UUID, ResourceWithSchedulesResponse> resourceMap = new HashMap<>();

        for (Object[] result : results) {
            Resource resource = (Resource) result[0];
            Schedule schedule = (Schedule) result[1];

            resourceMap.computeIfAbsent(resource.getId(), key -> new ResourceWithSchedulesResponse(
                    resource.getId(),
                    resource.getName(),
                    resource.getStatus().name(),
                    new ArrayList<>()
            )).getSchedules().add(new ScheduleSimpleResponse(schedule.toAggregate()));
        }

        List<ResourceWithSchedulesResponse> responses = new ArrayList<>(resourceMap.values());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responses.size());
        List<ResourceWithSchedulesResponse> paginatedResponses = responses.subList(start, end);

        return new PaginatedResponse(
                paginatedResponses,
                pageable.getPageNumber(),
                paginatedResponses.size(),
                (long) responses.size(),
                pageable.getPageSize(),
                pageable.getPageNumber()
        );
    }

    public static List<LocalDate> generateDateRange(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return dates;
    }

    private <E extends Enum<E>> void convertEnumFilter(FilterCriteria filter, Class<E> enumClass) {
        try {
            filter.setValue(Enum.valueOf(enumClass, (String) filter.getValue()));
        } catch (IllegalArgumentException e) {
            System.err.println("Valor inválido para el tipo Enum " + enumClass.getSimpleName() + ": " + filter.getValue());
        }
    }

    private PaginatedResponse getPaginatedResponse(Page<Schedule> schedules) {
        List<ScheduleResponse> responses = schedules.stream()
                .map(schedule -> new ScheduleResponse(schedule.toAggregate()))
                .collect(Collectors.toList());
        return new PaginatedResponse(responses, schedules.getTotalPages(), schedules.getNumberOfElements(),
                schedules.getTotalElements(), schedules.getSize(), schedules.getNumber());
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, 
               key = "'scheduleById:' + #id", 
               unless = "#result == null")
    public ScheduleDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(Schedule::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(
                        new GlobalBusinessException(DomainErrorMessage.SCHEDULE_NOT_FOUND,
                                new ErrorField("id", "Schedule not found."))));
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, allEntries = true)
    public void delete(UUID id) {
        try {
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(
                    new GlobalBusinessException(DomainErrorMessage.NOT_DELETE,
                            new ErrorField("id", "Element cannot be deleted as it has a related element.")));
        }
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, allEntries = true)
    public void delete(Schedule schedule) {
        repositoryCommand.delete(schedule);
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, allEntries = true)
    public UUID create(ScheduleDto schedule) {
        List<Schedule> overlappingSchedules = repositoryQuery.findOverlappingSchedules(
                schedule.getResource().getId(),
                schedule.getDate(),
                schedule.getStartTime(),
                schedule.getEndingTime());

        if (overlappingSchedules.isEmpty()) {
            Schedule entity = repositoryCommand.save(new Schedule(schedule));
            return entity.getId();
        } else {
            return overlappingSchedules.get(0).getId();
        }
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, allEntries = true)
    public void createAll(List<ScheduleDto> schedules) {
        List<Schedule> entities = schedules.stream()
                .map(Schedule::new)
                .collect(Collectors.toList());
        repositoryCommand.saveAll(entities);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, allEntries = true)
    public void update(ScheduleDto schedule) {
        repositoryCommand.save(new Schedule(schedule));
    }

    @Override
    @Cacheable(value = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE,
            key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public List<AvailableDateDto> getAvailableDatesAndSlots(UUID resourceId, UUID businessId, LocalDate startDate, LocalDate endDate) {
        return repositoryQuery.findAvailableSchedulesByResourceAndBusinessAndDateRange(resourceId, businessId, startDate, endDate).stream()
                .collect(Collectors.groupingBy(ScheduleAvailabilityDto::getDate))
                .entrySet().stream()
                .map(entry -> new AvailableDateDto(entry.getKey(), entry.getValue().stream()
                        .map(s -> new AvailableTimeSlotDto(s.getStartTime(), s.getEndingTime(), s.getScheduleId()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Schedule> findOverlappingSchedules(UUID resourceId, LocalDate date, LocalTime startTime, LocalTime endingTime) {
        return repositoryQuery.findOverlappingSchedules(resourceId, date, startTime, endingTime);
    }

    @Override
    public List<ScheduleDto> findSchedulesWithEqualStock(LocalDate date) {
        return repositoryQuery.findSchedulesWithEqualStock(date).stream()
                .map(Schedule::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CalendarCacheConfig.SCHEDULE_AVAILABILITY_CACHE, 
               key = "'availableServices:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #filterCriteria?.hashCode()", 
               unless = "#result == null")
    public PaginatedResponse getUniqueAvailableServices(Pageable pageable, List<FilterCriteria> filterCriteria) {

        filterCriteria.add(new FilterCriteria("stock", SearchOperation.GREATER_THAN, 0, LogicalOperation.AND));
        filterCriteria.add(new FilterCriteria("date", SearchOperation.GREATER_THAN, LocalDate.now(), LogicalOperation.AND));
        GenericSpecificationsBuilder<Schedule> specificationsBuilder = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Schedule> availableSchedules = repositoryQuery.findAll(specificationsBuilder, pageable);

        List<ServiceDto> serviceDtos = availableSchedules.stream()
                .map(Schedule::getService)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(Services::getId))))
                .stream()
                .map(Services::toAggregate).distinct().collect(Collectors.toList());
        int start = Math.min((int) pageable.getOffset(), serviceDtos.size());
        int end = Math.min((start + pageable.getPageSize()), serviceDtos.size());
        Page<ServiceDto> pagedServiceDtos = new PageImpl<>(serviceDtos.subList(start, end), pageable, serviceDtos.size());


        return new PaginatedResponse(
                pagedServiceDtos.getContent(),
                pagedServiceDtos.getTotalPages(),
                pagedServiceDtos.getNumberOfElements(),
                pagedServiceDtos.getTotalElements(),
                pagedServiceDtos.getSize(),
                pagedServiceDtos.getNumber()
        );
    }

    @Override
    public boolean existsOverlappingSchedule(UUID resourceId, LocalDate date, LocalTime startTime, LocalTime endingTime) {
        return this.repositoryQuery.existsOverlappingSchedule(resourceId, date, startTime, endingTime);
    }
}