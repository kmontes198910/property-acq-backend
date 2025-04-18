package com.kynsof.calendar.application.service;

import com.kynsof.calendar.application.query.businessService.GetServicesSimpleByBusinessId.ServiceSimple;
import com.kynsof.calendar.application.query.businessService.getbyid.BusinessServicesResponse;
import com.kynsof.calendar.application.query.service.ServicesResponse;
import com.kynsof.calendar.domain.dto.BusinessServicePriceResponse;
import com.kynsof.calendar.domain.dto.BusinessServicesDto;
import com.kynsof.calendar.domain.service.IBusinessServicesService;
import com.kynsof.calendar.infrastructure.config.CalendarCacheConfig;
import com.kynsof.calendar.infrastructure.entity.BusinessServices;
import com.kynsof.calendar.infrastructure.repository.command.BusinessServicesWriteDataJPARepository;
import com.kynsof.calendar.infrastructure.repository.query.BusinessServicesReadDataJPARepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BusinessServicesServiceImpl implements IBusinessServicesService {

    private final BusinessServicesWriteDataJPARepository repositoryCommand;
    private final BusinessServicesReadDataJPARepository repositoryQuery;

    public BusinessServicesServiceImpl(BusinessServicesWriteDataJPARepository repositoryCommand, BusinessServicesReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, allEntries = true)
    public void create(BusinessServicesDto object) {
        this.repositoryCommand.save(new BusinessServices(object));
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, allEntries = true)
    public void update(BusinessServicesDto object) {
        BusinessServices update = new BusinessServices(object);
        this.repositoryCommand.save(update);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, allEntries = true)
    public void delete(BusinessServicesDto object) {
        try {
            this.repositoryCommand.deleteById(object.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, allEntries = true)
    public void deleteIds(List<UUID> ids) {
        this.repositoryCommand.deleteAllByIdInBatch(ids);
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, key = "'bs:' + #id", unless = "#result == null")
    public BusinessServicesDto findById(UUID id) {
        return this.repositoryQuery.findById(id)
                .map(BusinessServices::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND,
                        new ErrorField("id", "BusinessService not found."))));
    }

    @Override
     @Cacheable(value = CalendarCacheConfig.BUSINESS_SERVICE_CACHE,
            key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        var specifications = new GenericSpecificationsBuilder<BusinessServices>(filterCriteria);
        var data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<BusinessServices> data) {
        var responses = data.getContent().stream()
                .map(BusinessServices::toAggregate)
                .map(BusinessServicesResponse::new)
                .toList();
        return new PaginatedResponse(responses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, key = "'servByBiz:' + #businessId + ':' + #pageable.pageNumber + ':' + #pageable.pageSize", unless = "#result == null")
    public PaginatedResponse findServicesByBusinessId(Pageable pageable, UUID businessId) {
        var data = this.repositoryQuery.findServicesByBusinessId(businessId, pageable);
        var responses = data.getContent().stream()
                .map(s -> new BusinessServicePriceResponse(s.getPrice(), new ServicesResponse(s.getServices().toAggregate())))
                .toList();
        return new PaginatedResponse(responses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, key = "'simpleServByBiz:' + #businessId + ':' + #pageable.pageNumber + ':' + #pageable.pageSize", unless = "#result == null")
    public PaginatedResponse findServicesSimpleByBusinessId(Pageable pageable, UUID businessId) {
        var data = this.repositoryQuery.findServicesByBusinessId(businessId, pageable);
        var responses = data.getContent().stream()
                .map(s -> new ServiceSimple(s.getServices().getId(), s.getServices().getName(), s.getServices().getStatus()))
                .toList();
        return new PaginatedResponse(responses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, key = "'bizServIds:' + #businessId", unless = "#result == null || #result.isEmpty()")
    public List<UUID> findBusinessServiceIdByBusinessId(UUID businessId) {
        return this.repositoryQuery.findBusinessServicesIdByBusinessId(businessId);
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, key = "'servByRes:' + #resourceId + ':' + #pageable.pageNumber + ':' + #pageable.pageSize", unless = "#result == null")
    public PaginatedResponse findServicesByResourceId(Pageable pageable, UUID resourceId) {
        var data = this.repositoryQuery.findServicesByResourceId(resourceId, pageable);
        return getPaginatedResponse(data);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, allEntries = true)
    public void createAll(List<BusinessServicesDto> businessServicePrice) {
        this.repositoryCommand.saveAllAndFlush(businessServicePrice.stream().map(BusinessServices::new).toList());
    }

    @Override
    @Cacheable(cacheNames = CalendarCacheConfig.BUSINESS_SERVICE_CACHE, key = "'countRelations:' + #serviceId + ':' + #businessId", unless = "#result == null")
    public Long countRelationsBetweenServiceAndBusiness(UUID serviceId, UUID businessId) {
        return this.repositoryQuery.countRelationsBetweenServiceAndBusiness(serviceId, businessId);
    }
}