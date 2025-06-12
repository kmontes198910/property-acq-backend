package com.kynsof.calendar.domain.service;

import com.kynsof.calendar.domain.dto.BusinessServicesDto;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBusinessServicesService {
    void create(BusinessServicesDto object);
    void update(BusinessServicesDto object);
    void delete(BusinessServicesDto object);
    BusinessServicesDto findById(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
    PaginatedResponse findServicesByBusinessId(Pageable pageable, UUID businessId);
    PaginatedResponse findServicesByResourceId(Pageable pageable, UUID resourceId);
    List<UUID> findBusinessServiceIdByBusinessId( UUID businessId);
    void createAll(List<BusinessServicesDto> businessServicePrice);
    void deleteIds(List<UUID> ids);
    PaginatedResponse findServicesSimpleByBusinessId(Pageable pageable, UUID businessId);
    PaginatedResponse findServicesSimpleByBusinessIdAndServiceName(Pageable pageable, UUID businessId, String serviceName);
    Long countRelationsBetweenServiceAndBusiness(UUID serviceId, UUID businessId);
}