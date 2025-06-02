package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.CompanyResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.CompanyNotFoundException;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.CompanyReadDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.CompanyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyType;

@Service
public class CompanyService implements ICompanyService {

    private final CompanyWriteDataJPARepository repositoryCommand;

    private final CompanyReadDataJPARepository repositoryQuery;

    public CompanyService(CompanyWriteDataJPARepository repositoryCommand, CompanyReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public UUID create(CompanyDto contactPersonDto) {
        return repositoryCommand.save(new Company(contactPersonDto)).getId();
    }

    @Override
    public void update(CompanyDto contactPersonDto) {
        Optional<Company> contactPerson = this.repositoryQuery.findById(contactPersonDto.getId());
        if (contactPerson.isPresent()) {
            Company oldContact = contactPerson.get();

            oldContact.setTitle(contactPersonDto.getTitle());
            oldContact.setOwnershipPercentage(contactPersonDto.getOwnershipPercentage());
            oldContact.setSignatureAuthority(contactPersonDto.getSignatureAuthority());
            oldContact.setNotes(contactPersonDto.getNotes());
            oldContact.setUpdatedBy(contactPersonDto.getUpdatedBy());
            oldContact.setCompanyType(new CompanyType(contactPersonDto.getCompanyType()));

            // Guardar los cambios
            repositoryCommand.save(oldContact);
        } else {
            throw new CompanyNotFoundException(contactPersonDto.getId().toString(), "ID");
        }
    }

    @Override
    public CompanyDto findById(UUID id) {
        return this.repositoryQuery.findById(id)
                .map(Company::toAggregateSimple)
                .orElseThrow(() -> new CompanyNotFoundException(id.toString(), "ID"));
    }

    @Override
    public void delete(UUID id) {
        this.findById(id);
        this.repositoryCommand.deleteById(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Company> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Company> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Company> data) {
        List<CompanyResponse> objects = new ArrayList<>();
        for (Company p : data.getContent()) {
            objects.add(new CompanyResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
