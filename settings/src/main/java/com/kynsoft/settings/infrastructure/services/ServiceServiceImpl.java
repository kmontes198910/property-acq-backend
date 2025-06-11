package com.kynsoft.settings.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.settings.application.response.ServicesResponse;
import com.kynsoft.settings.domain.dto.ServiceDto;
import com.kynsoft.settings.domain.enums.EServiceStatus;
import com.kynsoft.settings.domain.services.IServiceService;
import com.kynsoft.settings.infrastructure.config.SettingsCacheConfig;
import com.kynsoft.settings.infrastructure.entity.Services;
import com.kynsoft.settings.infrastructure.repository.command.ServiceWriteDataJPARepository;
import com.kynsoft.settings.infrastructure.repository.query.ServiceReadDataJPARepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceServiceImpl implements IServiceService {

    private final ServiceWriteDataJPARepository repositoryCommand;

    private final ServiceReadDataJPARepository repositoryQuery;

    public ServiceServiceImpl(ServiceWriteDataJPARepository repositoryCommand,
                              ServiceReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    @CacheEvict(cacheNames = {SettingsCacheConfig.SERVICE_CACHE}, allEntries = true)
    public ServiceDto create(ServiceDto object) {
        object.setStatus(EServiceStatus.ACTIVE);
        Services serviceEntity = new Services(object);
        Services savedEntity = this.repositoryCommand.save(serviceEntity);
        return savedEntity.toAggregate();
    }

    @Override
    @CacheEvict(cacheNames = {SettingsCacheConfig.SERVICE_CACHE}, allEntries = true)
    public ServiceDto update(ServiceDto objectDto) {
        Services update = new Services(objectDto);
        update.setUpdatedAt(LocalDateTime.now());
        Services savedEntity =  this.repositoryCommand.save(update);
        return savedEntity.toAggregate();
    }

    @Override
    @CacheEvict(cacheNames = {SettingsCacheConfig.SERVICE_CACHE}, allEntries = true)
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    @Cacheable(cacheNames = SettingsCacheConfig.SERVICE_CACHE, key = "#id", unless = "#result == null")
    public ServiceDto findByIds(UUID id) {
        Optional<Services> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }

        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.SERVICE_NOT_FOUND, new ErrorField("id", "Service not found.")));
    }

    @Override
    @Cacheable(cacheNames = SettingsCacheConfig.SERVICE_CACHE, key = "'multiIds:' + #ids", unless = "#result == null || #result.isEmpty()")
    public List<ServiceDto> findByIds(List<UUID> ids) {
        List<Services> objects = this.repositoryQuery.findAllById(ids);
        return objects.stream().map(Services::toAggregate).toList();
    }

    @Override
    @Cacheable(value = SettingsCacheConfig.SERVICE_TYPE_CACHE,
            key = "'search:' + #pageable.pageNumber + ':' + #pageable.pageSize + ':' + #pageable.sort + ':' + T(java.util.Objects).hash(#filterCriteria)",
            unless = "#result == null")
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Service> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Services> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Services> data) {
        List<ServicesResponse> servicesResponses = new ArrayList<>();
        for (Services s : data.getContent()) {
            servicesResponses.add(new ServicesResponse(s.toAggregate()));
        }
        return new PaginatedResponse(servicesResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    @Cacheable(cacheNames = SettingsCacheConfig.SERVICE_CACHE, key = "'countByName:' + #name + ':' + #id", unless = "#result == null")
    public Long countByNameAndNotId(String name, UUID id) {
        return this.repositoryQuery.countByNameAndNotId(name, id);
    }

    @Override
    @Cacheable(cacheNames = SettingsCacheConfig.SERVICE_CACHE, key = "'countByCode:' + #code + ':' + #id", unless = "#result == null")
    public Long countByCodeAndNotId(String code, UUID id) {
        return this.repositoryQuery.countByCodeAndNotId(code, id);
    }


    @Override
    @Cacheable(cacheNames = SettingsCacheConfig.SERVICE_CACHE, key = "'allServices'", unless = "#result == null || #result.isEmpty()")
    public List<ServiceDto> findAllToReplicate() {
        List<Services> objects = this.repositoryQuery.findAll();
        List<ServiceDto> objectDtos = new ArrayList<>();
        for (Services object : objects) {
            objectDtos.add(object.toAggregate());
        }
        return objectDtos;
    }

}
