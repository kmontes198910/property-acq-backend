package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.ContactPersonResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ContactPersonDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.ContactPersonNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IContactPersonService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.ContactPerson;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ContactPersonWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ContactPersonReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactPersonService implements IContactPersonService {

    private final ContactPersonWriteDataJPARepository repositoryCommand;

    private final ContactPersonReadDataJPARepository repositoryQuery;

    private final ILegalEntityService legalEntityService;

    public ContactPersonService(ContactPersonWriteDataJPARepository repositoryCommand, ContactPersonReadDataJPARepository repositoryQuery, ILegalEntityService legalEntityService) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public UUID create(ContactPersonDto contactPersonDto) {
        //manejar la excepcion en caso de que legalEntityId no venga en el dto
        LegalEntity legalEntity = contactPersonDto.getLegalEntityId() != null
                ? new LegalEntity(legalEntityService.findById(contactPersonDto.getLegalEntityId()))
                : null;
        ContactPerson contactPerson = new ContactPerson(contactPersonDto, legalEntity);
        return repositoryCommand.save(contactPerson).getId();
    }

    @Override
    public void update(ContactPersonDto contactPersonDto) {
        Optional<ContactPerson> contactPerson = this.repositoryQuery.findById(contactPersonDto.getId());
        if (contactPerson.isPresent()) {
            ContactPerson oldContact = contactPerson.get();
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
            throw new ContactPersonNotFoundException(contactPersonDto.getId().toString(), "ID");
        }
    }

    @Override
    public ContactPersonDto findById(UUID id) {
        return this.repositoryQuery.findById(id)
                .map(ContactPerson::toAggregate)
                .orElseThrow(() -> new ContactPersonNotFoundException(id.toString(), "ID"));
    }

    @Override
    public void delete(UUID id) {
        this.findById(id);
        this.repositoryCommand.deleteById(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<ContactPerson> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<ContactPerson> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<ContactPerson> data) {
        List<ContactPersonResponse> objects = new ArrayList<>();
        for (ContactPerson p : data.getContent()) {
            objects.add(new ContactPersonResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
