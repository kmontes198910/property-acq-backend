package com.kynsof.calendar.application.service;

import com.kynsof.calendar.application.query.BusinessResponse;
import com.kynsof.calendar.domain.dto.BusinessDto;
import com.kynsof.calendar.domain.dto.ScheduleServiceInfoDto;
import com.kynsof.calendar.domain.service.IBusinessService;
import com.kynsof.calendar.infrastructure.config.CalendarCacheConfig;
import com.kynsof.calendar.infrastructure.entity.Business;
import com.kynsof.calendar.infrastructure.repository.command.BusinessWriteDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.query.BusinessReadDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.query.ScheduleReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BusinessServiceImpl implements IBusinessService {

    private final BusinessWriteDataJPARepository repositoryCommand;

    private final BusinessReadDataJPARepository repositoryQuery;

    private final ScheduleReadDataJPARepository scheduleReadDataJPARepository;

    public BusinessServiceImpl(BusinessWriteDataJPARepository repositoryCommand,
                               BusinessReadDataJPARepository repositoryQuery,
                               ScheduleReadDataJPARepository scheduleReadDataJPARepository) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.scheduleReadDataJPARepository = scheduleReadDataJPARepository;
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, allEntries = true)
    public void create(BusinessDto object) {
        this.repositoryCommand.save(new Business(object));
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, allEntries = true)
    public void update(BusinessDto objectDto) {
        if (objectDto.getId() == null || objectDto == null) {
            throw new BusinessException(DomainErrorMessage.BUSINESS_OR_ID_NULL, "Business DTO or ID cannot be null.");
        }

        this.repositoryQuery.findById(objectDto.getId())
                .map(object -> {
                    if (objectDto.getName() != null) {
                        object.setName(objectDto.getName());
                    }
                    if (objectDto.getLatitude()!= null) {
                        object.setLatitude(objectDto.getLatitude());
                    }
                    if (objectDto.getLongitude() != null) {
                        object.setLongitude(objectDto.getLongitude());
                    }
                    if (objectDto.getAddress() != null) {
                        object.setAddress(objectDto.getAddress());
                    }
                    if (objectDto.getLogo() != null) {
                        object.setLogo(objectDto.getLogo());
                    }

                    return this.repositoryCommand.save(object);
                })
                .orElseThrow(() -> new BusinessException(DomainErrorMessage.QUALIFICATION_NOT_FOUND, "Qualification not found."));
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, allEntries = true)
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, allEntries = true)
    public void deleteIds(List<UUID> ids) {
        this.repositoryCommand.deleteAllByIdInBatch(ids);
    }

    @Cacheable(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, key = "#id", unless = "#result == null")
    @Override
    public BusinessDto findById(UUID id) {
        Optional<Business> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }
        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, new ErrorField("id", "Business not found.")));
    }

    @Override
    @Cacheable(value = CalendarCacheConfig.BUSINESS_SERVICE_CACHE,
            key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Business> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Business> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, 
               key = "'availableStock:' + #date + ':' + #serviceId + ':' + #pageable.pageNumber + ':' + #pageable.pageSize", 
               unless = "#result == null")
    public PaginatedResponse findBusinessesWithAvailableStockByDateAndService(LocalDate date, UUID serviceId,
                                                                              Pageable pageable) {
        Page<Business> data = scheduleReadDataJPARepository.findBusinessesWithAvailableStockByDateAndService(date, serviceId, pageable);
        return getPaginatedResponse(data);
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, 
               key = "'detailedSchedules:' + #startDate + ':' + #endDate + ':' + #serviceId + ':' + #businessName + ':' + #pageable.pageNumber + ':' + #pageable.pageSize", 
               unless = "#result == null")
    public PaginatedResponse findDetailedAvailableSchedulesByResourceAndBusinessAndDateRange(LocalDate startDate,
                                                                                             LocalDate endDate, UUID serviceId, String businessName,
                                                                              Pageable pageable) {
        Page<ScheduleServiceInfoDto> data = scheduleReadDataJPARepository.
                findDetailedAvailableSchedulesByResourceAndBusinessAndDateRange(serviceId, startDate, endDate, businessName, pageable);
        return new PaginatedResponse(data.stream().toList(), data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    private PaginatedResponse getPaginatedResponse(Page<Business> data) {
        List<BusinessResponse> businessResponses = new ArrayList<>();
        for (Business o : data.getContent()) {
            businessResponses.add(new BusinessResponse(o.toAggregate()));
        }
        return new PaginatedResponse(businessResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
