package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.CompanyContactResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.CompanyContactNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyContact;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.CompanyContactWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.CompanyContactReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class CompanyContactServiceImpl implements ICompanyContactService {

    private final CompanyContactReadDataJPARepository repositoryQuery;
    private final CompanyContactWriteDataJPARepository repositoryCommand;

    public CompanyContactServiceImpl(CompanyContactReadDataJPARepository repositoryQuery, 
                                     CompanyContactWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(CompanyContactDto object) {
        return repositoryCommand.save(new CompanyContact(object)).getId();
    }

    @Override
    @Transactional
    public void update(CompanyContactDto object) {
        CompanyContact update = this.findByIdSimple(object.getId());

        update.setCompany(new Company(object.getCompany()));
        update.setFirstName(object.getFirstName());
        update.setLastName(object.getLastName());
        update.setEmail(object.getEmail());
        update.setPhoneNumber(object.getPhoneNumber());
        update.setPosition(object.getPosition());
        update.setDepartment(object.getDepartment());
        update.setCategory(object.getCategory());
        update.setNotes(object.getNotes());
        update.setIsActive(object.getIsActive());
        

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public CompanyContactDto findById(UUID id) {
        Optional<CompanyContact> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateSimple();
        }
        throw new CompanyContactNotFoundException(id);
    }

    private CompanyContact findByIdSimple(UUID id) {
        Optional<CompanyContact> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new CompanyContactNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<CompanyContact> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<CompanyContact> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<CompanyContact> data) {
        List<CompanyContactResponse> objects = new ArrayList<>();
        for (CompanyContact p : data.getContent()) {
            objects.add(new CompanyContactResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
