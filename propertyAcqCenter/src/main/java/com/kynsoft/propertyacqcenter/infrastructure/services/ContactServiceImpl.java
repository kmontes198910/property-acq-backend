package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import com.kynsoft.propertyacqcenter.domain.services.IContactService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Contact;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.ContactWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.ContactReadDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.LegalEntityReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements IContactService {

    private final ContactReadDataJPARepository repositoryQuery;
    private final ContactWriteDataJPARepository repositoryCommand;
    private final LegalEntityReadDataJPARepository legalEntityRepository;

    public ContactServiceImpl(
            ContactReadDataJPARepository repositoryQuery,
            ContactWriteDataJPARepository repositoryCommand,
            LegalEntityReadDataJPARepository legalEntityRepository) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
        this.legalEntityRepository = legalEntityRepository;
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
            
            // Guardar los cambios
            repositoryCommand.save(contact);
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repositoryCommand.deleteById(id);
    }

    @Override
    public ContactDto findById(UUID id) {
        return repositoryQuery.findById(id)
                .map(Contact::toAggregate)
                .orElse(null);
    }

    @Override
    public ContactDto findByEmail(String email) {
        return repositoryQuery.findByEmail(email)
                .map(Contact::toAggregate)
                .orElse(null);
    }

    @Override
    public List<ContactDto> search(Pageable pageable, List<Object> filterCriteria) {
        // Implementación genérica que devuelve todos los contactos con paginación
        // Se puede mejorar añadiendo filtros específicos según los criterios
        return repositoryQuery.findAll(pageable)
                .stream()
                .map(Contact::toAggregate)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactDto> findByLegalEntityId(UUID legalEntityId) {
        return repositoryQuery.findByLegalEntityId(legalEntityId)
                .stream()
                .map(Contact::toAggregate)
                .collect(Collectors.toList());
    }
}