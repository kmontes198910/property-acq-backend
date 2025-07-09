package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.ContactResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.ContactNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Contact;
import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCategory;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ContactWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ContactReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements IContactService {

    private final ContactReadDataJPARepository repositoryQuery;
    private final ContactWriteDataJPARepository repositoryCommand;

    public ContactServiceImpl(ContactReadDataJPARepository repositoryQuery,
                              ContactWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(ContactDto contactDto) {
        // Asignar un nuevo UUID si no tiene uno
        if (contactDto.getId() == null) {
            contactDto.setId(UUID.randomUUID());
        }
        
        // Establecer valores por defecto para campos no inicializados
        if (contactDto.getIsActive() == null) {
            contactDto.setIsActive(true);
        }
        
        // Convertir DTO a entidad
        Contact contact = new Contact(contactDto);

        // Guardar la entidad y devolver su ID
        contact = repositoryCommand.save(contact);
        return contact.getId();
    }

    @Override
    @Transactional
    public void update(ContactDto contactDto) {
        // Verificar que exista el contacto
        Optional<Contact> existingContact = repositoryQuery.findById(contactDto.getId());
        if (existingContact.isPresent()) {
            Contact contact = existingContact.get();

            // Actualizar propiedades del contacto
            contact.setFirstName(contactDto.getFirstName());
            contact.setLastName(contactDto.getLastName());
            contact.setEmail(contactDto.getEmail());
            contact.setPhoneNumber(contactDto.getPhoneNumber());
            contact.setPosition(contactDto.getPosition());
            contact.setDepartment(contactDto.getDepartment());
            contact.setCategory(contactDto.getCategory());
            contact.setNotes(contactDto.getNotes());
            contact.setIsActive(contactDto.getIsActive());
            contact.setSubCategory(new SubCategory(contactDto.getSubCategory()));
            contact.setPersonalEmail(contactDto.getPersonalEmail());
            contact.setIsEmployee(contactDto.getIsEmployee());

            // Guardar los cambios
            repositoryCommand.save(contact);
        }
    }

    @Override
    public void delete(UUID id) {
        try {
            this.findById(id);
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new NotDeleteException();
        }
    }

    @Override
    public ContactDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(Contact::toAggregateSimple)
                .orElseThrow(()-> new ContactNotFoundException(id.toString(), "ID"));
    }

    @Override
    public ContactDto findByEmail(String email) {
        return repositoryQuery.findByEmail(email)
                .map(Contact::toAggregateSimple)
                .orElseThrow(()-> new ContactNotFoundException(email, "email"));
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        // Implementación genérica que devuelve todos los contactos con paginación
        // Se puede mejorar añadiendo filtros específicos según los criterios
        GenericSpecificationsBuilder<Contact> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Contact> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    @Override
    public List<ContactDto> findByLegalEntityId(UUID legalEntityId) {
        return repositoryQuery.findByLegalEntityId(legalEntityId)
                .stream()
                .map(Contact::toAggregate)
                .collect(Collectors.toList());
    }

    private PaginatedResponse getPaginatedResponse(Page<Contact> data) {
        List<ContactResponse> objects = new ArrayList<>();
        for (Contact p : data.getContent()) {
            objects.add(new ContactResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public long countByEmail(String email) {
        return this.repositoryQuery.countByEmail(email);
    }
}