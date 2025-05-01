package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.CompanyResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.CompanyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IContactPersonService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.CompanyReadDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.CompanyWriteDataJPARepository;

@Service
public class ContactPersonService implements IContactPersonService {

    private final CompanyWriteDataJPARepository repositoryCommand;

    private final CompanyReadDataJPARepository repositoryQuery;

    private final ILegalEntityService legalEntityService;

    public ContactPersonService(CompanyWriteDataJPARepository repositoryCommand, CompanyReadDataJPARepository repositoryQuery, ILegalEntityService legalEntityService) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public UUID create(CompanyDto contactPersonDto) {
        //manejar la excepcion en caso de que legalEntityId no venga en el dto
        LegalEntity legalEntity = contactPersonDto.getLegalEntityId() != null
                ? new LegalEntity(legalEntityService.findById(contactPersonDto.getLegalEntityId()))
                : null;
        Company contactPerson = new Company(contactPersonDto, legalEntity);
        return repositoryCommand.save(contactPerson).getId();
    }

    @Override
    public void update(CompanyDto contactPersonDto) {
        Optional<Company> contactPerson = this.repositoryQuery.findById(contactPersonDto.getId());
        if (contactPerson.isPresent()) {
            Company oldContact = contactPerson.get();
            if (contactPersonDto.getLegalEntityId() != null) {
                // Si el ID de LegalEntity ha cambiado, o no tenía antes
                if (oldContact.getLegalEntity() == null ||
                        !oldContact.getLegalEntity().getId().equals(contactPersonDto.getLegalEntityId())) {
                    LegalEntity legalEntity = new LegalEntity(legalEntityService.findById(contactPersonDto.getLegalEntityId()));
                    oldContact.setLegalEntity(legalEntity);
                }
            }
            // Actualizar los demás campos
            oldContact.setFirstName(contactPersonDto.getFirstName());
            oldContact.setLastName(contactPersonDto.getLastName());
            oldContact.setRole(contactPersonDto.getRole());
            oldContact.setEmail(contactPersonDto.getEmail());
            oldContact.setPhone(contactPersonDto.getPhone());
            oldContact.setCellPhone(contactPersonDto.getCellPhone());
            oldContact.setTitle(contactPersonDto.getTitle());
            oldContact.setDateOfBirth(contactPersonDto.getDateOfBirth());
            oldContact.setPersonalTaxId(contactPersonDto.getPersonalTaxId());
            oldContact.setNationality(contactPersonDto.getNationality());
            oldContact.setPersonalAddress(contactPersonDto.getPersonalAddress());
            oldContact.setCity(contactPersonDto.getCity());
            oldContact.setState(contactPersonDto.getState());
            oldContact.setZipCode(contactPersonDto.getZipCode());
            oldContact.setPersonalEmail(contactPersonDto.getPersonalEmail());
            oldContact.setIsPrimary(contactPersonDto.getIsPrimary());
            oldContact.setOwnershipPercentage(contactPersonDto.getOwnershipPercentage());
            oldContact.setSignatureAuthority(contactPersonDto.getSignatureAuthority());
            oldContact.setNotes(contactPersonDto.getNotes());
            oldContact.setUpdatedBy(contactPersonDto.getUpdatedBy());

            // Guardar los cambios
            repositoryCommand.save(oldContact);
        } else {
            throw new CompanyNotFoundException(contactPersonDto.getId().toString(), "ID");
        }
    }

    @Override
    public CompanyDto findById(UUID id) {
        return this.repositoryQuery.findById(id)
                .map(Company::toAggregate)
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
