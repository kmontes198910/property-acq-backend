package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.ContactResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.ContactNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Business;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Contact;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ContactWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ContactReadDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.LegalEntityReadDataJPARepository;
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
    private final LegalEntityReadDataJPARepository legalEntityRepository;
    private final IBusinessService businessService;

    public ContactServiceImpl(
            ContactReadDataJPARepository repositoryQuery,
            ContactWriteDataJPARepository repositoryCommand,
            LegalEntityReadDataJPARepository legalEntityRepository, IBusinessService businessService) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
        this.legalEntityRepository = legalEntityRepository;
        this.businessService = businessService;
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
        
        // Establecer relación con LegalEntity si existe ID
        if (contactDto.getLegalEntityId() != null) {
            Optional<LegalEntity> legalEntity = legalEntityRepository.findById(contactDto.getLegalEntityId());
            legalEntity.ifPresent(contact::setLegalEntity);
        }

        // Establecer relación con Business si existe ID
        if (contactDto.getBusinessId() != null) {
            Business business = new Business(businessService.findById(contactDto.getBusinessId()));
            contact.setBusiness(business);
        }
        
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
            contact.setCompany(contactDto.getCompany());
            contact.setNotes(contactDto.getNotes());
            contact.setIsActive(contactDto.getIsActive());
            
            // Actualizar relación con LegalEntity si cambia
            if (contactDto.getLegalEntityId() != null) {
                // Si el ID de LegalEntity ha cambiado, o no tenía antes
                if (contact.getLegalEntity() == null || 
                        !contact.getLegalEntity().getId().equals(contactDto.getLegalEntityId())) {
                    legalEntityRepository.findById(contactDto.getLegalEntityId())
                            .ifPresent(contact::setLegalEntity);
                }
            } else {
                // Si se quiere eliminar la relación
                contact.setLegalEntity(null);
            }

            // Actualizar relación con Business si cambia
            if (contactDto.getBusinessId() != null) {
                // Si el ID de LegalEntity ha cambiado, o no tenía antes
                if (contact.getBusiness() == null ||
                        !contact.getBusiness().getId().equals(contactDto.getBusinessId())) {
                    Business business = new Business(businessService.findById(contactDto.getBusinessId()));
                    contact.setBusiness(business);
                }
            } else {
                // Si se quiere eliminar la relación
                contact.setLegalEntity(null);
            }
            
            // Guardar los cambios
            repositoryCommand.save(contact);
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public ContactDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(Contact::toAggregate)
                .orElseThrow(()-> new ContactNotFoundException(id.toString(), "ID"));
    }

    @Override
    public ContactDto findByEmail(String email) {
        return repositoryQuery.findByEmail(email)
                .map(Contact::toAggregate)
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
}