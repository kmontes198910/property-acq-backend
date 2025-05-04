package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.CompanyAddressResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyAddressDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.CompanyAddressNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyAddressService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyAddress;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.CompanyAddressWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.CompanyAddressReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class CompanyAddressServiceImpl implements ICompanyAddressService {

    private final CompanyAddressReadDataJPARepository repositoryQuery;
    private final CompanyAddressWriteDataJPARepository repositoryCommand;

    public CompanyAddressServiceImpl(CompanyAddressReadDataJPARepository repositoryQuery, 
                                     CompanyAddressWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(CompanyAddressDto object) {
        return repositoryCommand.save(new CompanyAddress(object)).getId();
    }

    @Override
    @Transactional
    public void update(CompanyAddressDto object) {
        CompanyAddress update = this.findByIdSimple(object.getId());

        update.setCompany(new Company(object.getCompany()));
        update.setAddressType(object.getAddressType());
        update.setCity(object.getCity());
        update.setCountry(object.getCountry());
        update.setIsPrimary(object.getIsPrimary());
        update.setState(object.getState());
        update.setStreetAddress1(object.getStreetAddress1());
        update.setStreetAddress2(object.getStreetAddress2());
        update.setZipCode(object.getZipCode());
        update.setUpdatedBy(object.getUpdatedBy());

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public CompanyAddressDto findById(UUID id) {
        Optional<CompanyAddress> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateSimple();
        }
        throw new CompanyAddressNotFoundException(id);
    }

    private CompanyAddress findByIdSimple(UUID id) {
        Optional<CompanyAddress> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new CompanyAddressNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<CompanyAddress> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<CompanyAddress> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<CompanyAddress> data) {
        List<CompanyAddressResponse> objects = new ArrayList<>();
        for (CompanyAddress p : data.getContent()) {
            objects.add(new CompanyAddressResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
