package com.kynsoft.finamer.digitalsignature.infrastructure.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;

import com.kynsoft.finamer.digitalsignature.domain.dto.BusinessDto;
import com.kynsoft.finamer.digitalsignature.domain.service.IBusinessService;
import com.kynsoft.finamer.digitalsignature.infrastructure.entity.Business;
import com.kynsoft.finamer.digitalsignature.infrastructure.http.BusinessHttp;
import com.kynsoft.finamer.digitalsignature.infrastructure.http.BusinessHttpUUIDService;
import com.kynsoft.finamer.digitalsignature.infrastructure.repository.command.BusinessWriteDataJPARepository;
import com.kynsoft.finamer.digitalsignature.infrastructure.repository.query.BusinessReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


}
