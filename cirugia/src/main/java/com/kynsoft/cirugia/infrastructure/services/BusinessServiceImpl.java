package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.application.response.BusinessResponse;
import com.kynsoft.cirugia.domain.dto.BusinessDto;
import com.kynsoft.cirugia.domain.dto.exception.BusinessNotFoundException;
import com.kynsoft.cirugia.domain.service.IBusinessService;
import com.kynsoft.cirugia.infrastructure.entities.Business;
import com.kynsoft.cirugia.infrastructure.repository.command.BusinessWriteDataJPARepository;
import com.kynsoft.cirugia.infrastructure.repository.query.BusinessReadDataJPARepository;
import com.kynsoft.cirugia.infrastructure.services.http.BusinessHttpUUIDService;
import com.kynsoft.cirugia.infrastructure.services.http.dto.BusinessHttp;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class BusinessServiceImpl implements IBusinessService {

    private final BusinessReadDataJPARepository repositoryQuery;
    private final BusinessWriteDataJPARepository repositoryCommand;
    private final BusinessHttpUUIDService businessHttpUUIDService;

    public BusinessServiceImpl(BusinessReadDataJPARepository repositoryQuery, 
                               BusinessWriteDataJPARepository repositoryCommand,
                               BusinessHttpUUIDService businessHttpUUIDService) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
        this.businessHttpUUIDService = businessHttpUUIDService;
    }

    @Override
    @Transactional
    public UUID create(BusinessDto object) {
        return repositoryCommand.save(new Business(object)).getId();
    }

    @Override
    @Transactional
    public void update(BusinessDto object) {
        Business business = new Business(object);
        business.setUpdatedAt(LocalDateTime.now());
        repositoryCommand.save(business);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public BusinessDto findById(UUID id) {
        Optional<Business> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        } else {
            BusinessHttp response = this.businessHttpUUIDService.sendGetHttpRequest(id);
            BusinessDto businessDto = new BusinessDto(
                    response.getId(), 
                    response.getName(), 
                    response.getLatitude(), 
                    response.getLongitude(), 
                    response.getAddress(), 
                    response.getLogo(), 
                    response.getPhone(), 
                    response.getEmail(), 
                    response.getRuc()
            );
            this.repositoryCommand.save(new Business(businessDto));
            return businessDto;
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Business> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Business> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Business> data) {
        List<BusinessResponse> objects = new ArrayList<>();
        for (Business p : data.getContent()) {
            objects.add(new BusinessResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
