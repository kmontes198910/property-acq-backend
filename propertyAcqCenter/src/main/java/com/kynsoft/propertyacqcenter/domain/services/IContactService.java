package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.ContactDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interfaz para el servicio de contactos
 */
public interface IContactService {
    
    /**
     * Crea un nuevo contacto
     * @param contactDto datos del contacto a crear
     * @return UUID del contacto creado
     */
    UUID create(ContactDto contactDto);
    
    /**
     * Actualiza un contacto existente
     * @param contactDto datos del contacto a actualizar
     */
    void update(ContactDto contactDto);
    
    /**
     * Elimina un contacto por su ID
     * @param id UUID del contacto a eliminar
     */
    void delete(UUID id);
    
    /**
     * Busca un contacto por su ID
     * @param id UUID del contacto a buscar
     * @return ContactDto si se encuentra, null en caso contrario
     */
    ContactDto findById(UUID id);
    
    /**
     * Busca un contacto por su email
     * @param email Email del contacto a buscar
     * @return ContactDto si se encuentra, null en caso contrario
     */
    ContactDto findByEmail(String email);
    
    /**
     * Busca contactos con criterios de filtrado y paginación
     * @param pageable configuración de paginación
     * @param filterCriteria criterios de filtrado
     * @return Lista de ContactDto que coinciden con los criterios
     */
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
    
    /**
     * Busca todos los contactos asociados a una entidad legal
     * @param legalEntityId UUID de la entidad legal
     * @return Lista de ContactDto asociados a la entidad legal
     */
    List<ContactDto> findByLegalEntityId(UUID legalEntityId);

    long countByEmail(String email);
}