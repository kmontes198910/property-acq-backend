package com.kynsof.calendar.application.service;

import com.kynsof.calendar.application.query.ResourceResponse;
import com.kynsof.calendar.domain.dto.ResourceDto;
import com.kynsof.calendar.domain.dto.ResourceWithSchedulesDto;
import com.kynsof.calendar.domain.dto.ScheduleDto;
import com.kynsof.calendar.domain.dto.ServiceDto;
import com.kynsof.calendar.domain.dto.enumType.EResourceStatus;
import com.kynsof.calendar.domain.service.IResourceService;
import com.kynsof.calendar.infrastructure.config.CalendarCacheConfig;
import com.kynsof.calendar.infrastructure.entity.*;
import com.kynsof.calendar.infrastructure.repository.command.BusinessResourceWriteDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.command.ResourceServiceWriteDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.command.ResourceWriteDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.query.*;
import com.kynsof.calendar.application.service.http.DoctorHttpUUIDService;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.http.entity.DoctorHttp;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.share.utils.ConfigureTimeZone;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements IResourceService {

    private final ResourceWriteDataJPARepository repositoryCommand;
    private final ResourceReadDataJPARepository repositoryQuery;
    private final ScheduleReadDataJPARepository scheduleReadDataJPARepository;
    private final BusinessReadDataJPARepository businessReadDataJPARepository;
    private final ServiceReadDataJPARepository serviceReadRepository;
    private final ResourceServiceWriteDataJPARepository resourceServiceWriteDataJPARepository;
    private final ResourceServicesReadDataJPARepository resourceServiceReadDataJPARepository;
    private final BusinessResourceWriteDataJPARepository businessResourceWriteDataJPARepository;
    private final DoctorHttpUUIDService doctorHttpUUIDService;

    public ResourceServiceImpl(ResourceWriteDataJPARepository repositoryCommand, ResourceReadDataJPARepository repositoryQuery, ScheduleReadDataJPARepository scheduleReadDataJPARepository, BusinessReadDataJPARepository businessReadDataJPARepository, ServiceReadDataJPARepository serviceReadRepository, ResourceServiceWriteDataJPARepository resourceServiceWriteDataJPARepository, ResourceServicesReadDataJPARepository resourceServiceReadDataJPARepository, BusinessResourceWriteDataJPARepository businessResourceWriteDataJPARepository, DoctorHttpUUIDService doctorHttpUUIDService) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.scheduleReadDataJPARepository = scheduleReadDataJPARepository;
        this.businessReadDataJPARepository = businessReadDataJPARepository;
        this.serviceReadRepository = serviceReadRepository;
        this.resourceServiceWriteDataJPARepository = resourceServiceWriteDataJPARepository;
        this.resourceServiceReadDataJPARepository = resourceServiceReadDataJPARepository;
        this.businessResourceWriteDataJPARepository = businessResourceWriteDataJPARepository;
        this.doctorHttpUUIDService = doctorHttpUUIDService;
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.RESOURCE_SERVICE_CACHE, allEntries = true)
    public void create(ResourceDto object) {
        object.setStatus(EResourceStatus.ACTIVE);
        this.repositoryCommand.save(new Resource(object));
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.RESOURCE_SERVICE_CACHE, allEntries = true)
    public void update(ResourceDto objectDto) {
        Resource update = new Resource(objectDto);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(new Resource(objectDto));
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.RESOURCE_SERVICE_CACHE, allEntries = true)
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Cacheable(cacheNames = CalendarCacheConfig.RESOURCE_SERVICE_CACHE, key = "#id", unless = "#result == null")
    @Override
    public ResourceDto findById(UUID id) {
        Optional<Resource> patient = this.repositoryQuery.findById(id);
        if (patient.isPresent()) {
            return patient.get().toAggregate();
        }
        {
            DoctorHttp doctorHttp = this.doctorHttpUUIDService.sendGetHttpRequest(id);
            ResourceDto doctorDto = new ResourceDto(
                    doctorHttp.getId(),
                    doctorHttp.getName(),
                    doctorHttp.getImage(),
                    EResourceStatus.ACTIVE
            );
            this.repositoryCommand.save(new Resource(doctorDto));
            return doctorDto;
        }
    }

    @Override
    @Cacheable(value = CalendarCacheConfig.RESOURCE_SERVICE_CACHE,
            key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Resource> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Resource> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Resource> data) {
        List<ResourceResponse> patients = new ArrayList<>();
        for (Resource s : data.getContent()) {
            patients.add(new ResourceResponse(s.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.RESOURCE_SERVICE_CACHE,
            key = "'resWithSchedules:' + #businessId + ':' + #serviceId + ':' + #date + ':' + #pageable.pageNumber + ':' + #pageable.pageSize",
            unless = "#result == null")
    public PaginatedResponse findResourcesWithAvailableSchedules(UUID businessId, UUID serviceId, LocalDate date, Pageable pageable) {
        Page<Schedule> schedules = scheduleReadDataJPARepository.findSchedulesWithStockByBusinessServiceAndDate(businessId, serviceId, date, pageable);

        Map<UUID, List<Schedule>> schedulesByResourceId = schedules.stream()
                .collect(Collectors.groupingBy(s -> s.getResource().getId()));

        List<ResourceWithSchedulesDto> resourcesWithSchedules = new ArrayList<>();
        schedulesByResourceId.forEach((resourceId, schedulesList) -> {
            Resource resource = schedulesList.get(0).getResource();
            List<ScheduleDto> scheduleDtos = schedulesList.stream()
                    .map(Schedule::toAggregate)
                    .collect(Collectors.toList());

            resourcesWithSchedules.add(new ResourceWithSchedulesDto(resource.toAggregate(), scheduleDtos));
        });

        return new PaginatedResponse(resourcesWithSchedules, schedules.getTotalPages(), schedules.getNumberOfElements(),
                schedules.getTotalElements(), schedules.getSize(), schedules.getNumber());
    }

    @Override
    @CacheEvict(cacheNames = {CalendarCacheConfig.RESOURCE_SERVICE_CACHE, CalendarCacheConfig.BUSINESS_RESOURCE_CACHE}, allEntries = true)
    public void addBusiness(UUID businessId, UUID userId, LocalDate date) {
        Optional<Resource> resource = this.repositoryQuery.findById(userId);
        Optional<Business> business = this.businessReadDataJPARepository.findById(businessId);
        if (resource.isPresent() && business.isPresent()) {
            Resource resourceObject = resource.get();
            Business businessObject = business.get();

            BusinessResource businessResource = new BusinessResource();
            businessResource.setId(UUID.randomUUID());
            businessResource.setBusiness(businessObject);
            businessResource.setResource(resourceObject);
            businessResource.setCreatedAt(ConfigureTimeZone.getTimeZone());
            this.businessResourceWriteDataJPARepository.save(businessResource);
        }
    }

    @Override
    @CacheEvict(cacheNames = CalendarCacheConfig.RESOURCE_SERVICE_CACHE, allEntries = true)
    public void addServicesToResource(UUID resourceId, List<UUID> serviceIds) {
        Optional<Resource> resource = this.repositoryQuery.findById(resourceId);
        Resource resourceObject = resource.get();

        List<UUID> resourceServices = this.resourceServiceReadDataJPARepository.FindResourceServiceByResourceId(resourceId);

        if (!resourceServices.isEmpty()) {
            this.resourceServiceWriteDataJPARepository.deleteAllById(resourceServices);
        }
        List<Services> servicesToAdd = this.serviceReadRepository.findAllById(serviceIds);

        servicesToAdd.forEach(service -> {
            ResourceService resourceService = new ResourceService();
            resourceService.setService(service);
            resourceService.setResource(resourceObject);
            resourceService.setCreationDate(ConfigureTimeZone.getTimeZone());

            this.resourceServiceWriteDataJPARepository.save(resourceService);
        });
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.RESOURCE_SERVICE_CACHE,
            key = "'resByService:' + #businessId + ':' + #serviceId + ':' + #pageable.pageNumber + ':' + #pageable.pageSize",
            unless = "#result == null")
    public PaginatedResponse findResourcesByServiceId(UUID businessId, UUID serviceId, Pageable pageable) {
        Page<Resource> data = this.repositoryQuery.findResourcesByServiceIdAndBusinessId(serviceId, businessId, pageable);
        return getPaginatedResponse(data);
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.RESOURCE_SERVICE_CACHE,
            key = "'servicesByResAndBiz:' + #resourceId + ':' + #businessId",
            unless = "#result == null || #result.isEmpty()")
    public List<ServiceDto> getAllServicesByResourceAndBusiness(UUID resourceId, UUID businessId) {
        List<Services> services = repositoryQuery.findAllServicesByResourceAndBusiness(resourceId, businessId);
        return services.stream().map(Services::toAggregate).toList();
    }

}
